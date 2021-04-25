package com.example.visitormanagementapp.Registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.visitormanagementapp.Employee.HomeScreen;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Database.DatabaseRoomForExtra;
import com.example.visitormanagementapp.Interfaces.RoomDao;
import com.example.visitormanagementapp.Models.RoomEntityForExtraInfo;
import com.example.visitormanagementapp.Security.SecurityHomeScreen;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.example.visitormanagementapp.databinding.ActivityFillDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class FillDetailsActivity extends AppCompatActivity {

    ActivityFillDetailsBinding binding;
    Uri selectedImage;
    String imageUrl=" ";
    RoomEntityForExtraInfo roomEntityForExtraInfo;
    SharedPreference sharedPreference;
    DatabaseRoomForExtra databaseRoomForExtra;
    RoomDao roomDao;

    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_fill_details);

        sharedPreference = new SharedPreference();

        handleClickEvents();



    }

    private void handleClickEvents() {
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });



    }


    private void uploadPhoto() {
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null){
            if(data.getData()!=null){
                binding.image.setImageURI(data.getData());
                selectedImage=data.getData();
                imageUrl=selectedImage.toString();
            }
        }
    }


    private void checkDetails() {
        boolean isValid = true;
        if(TextUtils.isEmpty(binding.name.getText().toString())){
            binding.nameLayout.setError("Please enter your name");
            isValid = false;
        }
        if(TextUtils.isEmpty(binding.contact.getText().toString())){
            binding.contactLayout.setError("Please enter your contact number");
            isValid = false;
        }else{
            if(binding.contact.getText().toString().length() != 10){
                binding.contactLayout.setError("Please enter correct contact number");
                isValid = false;
            }
        }
        if(TextUtils.isEmpty(binding.department.getText().toString())){
            binding.departmentLayout.setError("Please enter your department");
            isValid = false;
        }
        if(imageUrl.equals(" ")){
            binding.upload.setError("Please upload a photo");
            isValid = false;
        }

        if(isValid){

            List<String> retrieveList = new ArrayList<>();
            retrieveList = sharedPreference.retrieveInformation(this, getIntent().getExtras().getString("UserStatus"));

            roomEntityForExtraInfo = new RoomEntityForExtraInfo(binding.name.getText().toString(), binding.contact.getText().toString(), binding.department.getText().toString(),
                    retrieveList.get(0), retrieveList.get(1), getIntent().getExtras().getString("UserStatus"), imageUrl);

            databaseRoomForExtra = DatabaseRoomForExtra.getDatabaseRoom(this);
            roomDao = databaseRoomForExtra.roomDao();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    roomDao.insertAll(roomEntityForExtraInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sharedPreference.setExtraInformation(getApplicationContext(), getIntent().getExtras().getString("UserStatus"));
                            if(getIntent().getStringExtra("UserStatus").equals("Security")){
                                startActivity(new Intent(getApplicationContext(), SecurityHomeScreen.class));
                            }else{
                                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                            }
                            finish();
                        }
                    });

                }
            }).start();

        }



    }

}