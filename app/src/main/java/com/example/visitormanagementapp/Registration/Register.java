package com.example.visitormanagementapp.Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.example.visitormanagementapp.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    SharedPreference sharedPreference;
    String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        sharedPreference = new SharedPreference();

        handleClickEvents();


    }

    private void handleClickEvents() {

        activityRegisterBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });


    }

    private void checkDetails() {
        boolean isValid = true;


        if(TextUtils.isEmpty(activityRegisterBinding.identificationNumber.getText().toString())){
            activityRegisterBinding.idLayout.setError("Please enter your identification number");
            isValid = false;
        }


        if(TextUtils.isEmpty(activityRegisterBinding.password.getText().toString())){
            activityRegisterBinding.passwordLayout.setError("Please enter your password");
            isValid = false;
        }else{
            if(activityRegisterBinding.password.getText().toString().length()<6){
                activityRegisterBinding.passwordLayout.setError("Please enter your password of atleast 6 characters");
                isValid = false;
            }
        }


        if(activityRegisterBinding.security.isChecked()){
            userStatus = activityRegisterBinding.security.getText().toString();
        }else if(activityRegisterBinding.employee.isChecked()){
            userStatus = activityRegisterBinding.employee.getText().toString();
        }else{
            activityRegisterBinding.security.setError("Please select one among the two");
            isValid = false;
        }


        if(isValid){

            String identificationNumber = activityRegisterBinding.identificationNumber.getText().toString();
            String password = activityRegisterBinding.password.getText().toString();

            sharedPreference.saveData(this, userStatus, identificationNumber, password, true, false);

            Intent intent = new Intent(getApplicationContext(), FillDetailsActivity.class);
            intent.putExtra("UserStatus", userStatus);
            startActivity(intent);
            finish();

        }


    }
}