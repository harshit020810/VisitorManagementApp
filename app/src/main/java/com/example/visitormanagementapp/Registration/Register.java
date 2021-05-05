package com.example.visitormanagementapp.Registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.example.visitormanagementapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    SharedPreference sharedPreference;
    String userStatus;
    FirebaseAuth mAuth;
    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    String uID, imageUrl;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    HashMap<String, Object> hashMap = new HashMap<>();

    Uri selectedImage;

    FirebaseUser user;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        sharedPreference = new SharedPreference();

        mAuth = FirebaseAuth.getInstance();

        handleClickEvents();

        progressDialog = new ProgressDialog(this);


    }

    private void handleClickEvents() {

        activityRegisterBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });

        activityRegisterBinding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });


    }

    private void checkDetails() {


        boolean isValid = true;


        if (TextUtils.isEmpty(activityRegisterBinding.email.getText().toString())) {
            activityRegisterBinding.emailIdLayout.setError("Please enter your identification number");
            isValid = false;
        } else {
            if (!(activityRegisterBinding.email.getText().toString().trim().matches(emailPattern))) {
                activityRegisterBinding.email.setError("Please enter a valid email address!");
                isValid = false;
            }
        }


        if (TextUtils.isEmpty(activityRegisterBinding.password.getText().toString())) {
            activityRegisterBinding.passwordLayout.setError("Please enter your password");
            isValid = false;
        } else {
            if (activityRegisterBinding.password.getText().toString().length() < 6) {
                activityRegisterBinding.passwordLayout.setError("Please enter your password of atleast 6 characters");
                isValid = false;
            }
        }


        if(TextUtils.isEmpty(activityRegisterBinding.name.getText().toString())){
            activityRegisterBinding.nameLayout.setError("Please enter your name");
            isValid = false;
        }


        if(TextUtils.isEmpty(activityRegisterBinding.contact.getText().toString())){
            activityRegisterBinding.contactLayout.setError("Please enter your contact number");
            isValid = false;
        }else{
            if(activityRegisterBinding.contact.getText().toString().length() != 10){
                activityRegisterBinding.contactLayout.setError("Please enter correct contact number");
                isValid = false;
            }
        }


        if(TextUtils.isEmpty(activityRegisterBinding.department.getText().toString())){
            activityRegisterBinding.departmentLayout.setError("Please enter your department");
            isValid = false;
        }

        if(TextUtils.isEmpty(activityRegisterBinding.identificationNumber.getText().toString())){
            activityRegisterBinding.idLayout.setError("Please enter your department");
            isValid = false;
        }

        if(imageUrl.equals(" ")){
            activityRegisterBinding.upload.setError("Please upload a photo");
            isValid = false;
        }

        if (activityRegisterBinding.security.isChecked()) {
            userStatus = activityRegisterBinding.security.getText().toString();
        } else if (activityRegisterBinding.employee.isChecked()) {
            userStatus = activityRegisterBinding.employee.getText().toString();
        } else {
            activityRegisterBinding.security.setError("Please select one among the two");
            isValid = false;
        }

        if (isValid) {

            progressDialog.setTitle("Registering User");
            progressDialog.setMessage("Please wait while we register your details");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            String emailAddress = activityRegisterBinding.email.getText().toString();
            String password = activityRegisterBinding.password.getText().toString();
            String name = activityRegisterBinding.name.getText().toString();
            String contact = activityRegisterBinding.contact.getText().toString();
            String department = activityRegisterBinding.department.getText().toString();
            String id = activityRegisterBinding.identificationNumber.getText().toString();


            registerUser(emailAddress, password, name, contact, department, imageUrl, id);

        }


    }

    private void registerUser(String emailAddress, String password, String name, String contact, String department, String imageUrl, String id) {

        hashMap.clear();
        hashMap.put("email", emailAddress);
        hashMap.put("password", password);
        hashMap.put("name", name);
        hashMap.put("contact", contact);
        hashMap.put("department", department);
        hashMap.put("image", imageUrl);
        hashMap.put("id", id);

        mAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    saveData(contact);
                }
            }
        });


    }

    private void saveData(String contact) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (userStatus.equals("Security")) {
                    if (!(snapshot.child("VMS SECURITY").child(contact).exists())) {
                        databaseReference.child("VMS SECURITY").child(contact).updateChildren(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Register.this, "Verification Email has been sent to your email Address", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Register.this, "Network Error", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else {
                    if (!(snapshot.child("VMS EMPLOYEE").child(contact).exists())) {
                        databaseReference.child("VMS EMPLOYEE").child(contact).updateChildren(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Register.this, "Verification Email has been sent to your email Address", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Register.this, "Network Error", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                activityRegisterBinding.image.setImageURI(data.getData());
                selectedImage=data.getData();
                imageUrl=selectedImage.toString();
            }
        }
    }
}