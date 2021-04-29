package com.example.visitormanagementapp.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.visitormanagementapp.Employee.HomeScreen;
import com.example.visitormanagementapp.Models.RegistrationModel;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Security.SecurityHomeScreen;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.example.visitormanagementapp.databinding.ActivityLogInBinding;
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

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding activityLogInBinding;
    String userStatus;
    SharedPreference sharedPreference;
    FirebaseAuth  mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user;
    RegistrationModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLogInBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in);

        sharedPreference = new SharedPreference();

        handleClickEvent();


    }

    private void handleClickEvent() {

        activityLogInBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });

    }

    private void checkDetails() {
        boolean isValid = true;

        if(TextUtils.isEmpty(activityLogInBinding.email.getText().toString())){
            activityLogInBinding.emailIdLayout.setError("Please enter your identification number");
            isValid = false;
        }


        if(TextUtils.isEmpty(activityLogInBinding.password.getText().toString())){
            activityLogInBinding.passwordLayout.setError("Please enter your password");
            isValid = false;
        }else{
            if(activityLogInBinding.password.getText().toString().length()<6){
                activityLogInBinding.passwordLayout.setError("Please enter your password of atleast 6 characters");
                isValid = false;
            }
        }

        if(TextUtils.isEmpty(activityLogInBinding.contact.getText().toString())){
            activityLogInBinding.contactLayout.setError("Please enter your contact number");
            isValid = false;
        }else{
            if(activityLogInBinding.contact.getText().toString().length() != 10){
                activityLogInBinding.contactLayout.setError("Please enter correct contact number");
                isValid = false;
            }
        }

        if(activityLogInBinding.security.isChecked()){
            userStatus = activityLogInBinding.security.getText().toString();
        }else if(activityLogInBinding.employee.isChecked()){
            userStatus = activityLogInBinding.employee.getText().toString();
        }else{
            activityLogInBinding.security.setError("Please select one among the two");
            isValid = false;
        }


        if(isValid){
            String emailAddress = activityLogInBinding.email.getText().toString();
            String password = activityLogInBinding.password.getText().toString();
            String contact = activityLogInBinding.contact.getText().toString();


            logInUser(emailAddress, password, contact);

        }

    }

    private void logInUser(String emailAddress, String password, String contact) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(userStatus.equals("Security")){
                    if(snapshot.child("VMS SECURITY").child(contact).exists()){
                       model = snapshot.child("VMS SECURITY").child(contact).getValue(RegistrationModel.class);
                       mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   user = mAuth.getCurrentUser();
                                   if(model.getEmail().equals(emailAddress) && model.getPassword().equals(password) && model.getContact().equals(contact)){
                                       if(user.isEmailVerified()){
                                           sharedPreference.saveData(getApplicationContext(), userStatus, emailAddress, password, true);
                                           startActivity(new Intent(getApplicationContext(), SecurityHomeScreen.class));
                                           finish();
                                       }else{
                                           Toast.makeText(LogIn.this, "Verify your email First", Toast.LENGTH_SHORT).show();
                                       }
                                   }else{
                                       Toast.makeText(LogIn.this, "Please check your credentials", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                       });
                    }
                }else{
                    if(snapshot.child("VMS EMPLOYEE").child(contact).exists()){
                        model = snapshot.child("VMS EMPLOYEE").child(contact).getValue(RegistrationModel.class);
                        mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    user = mAuth.getCurrentUser();
                                    if(model.getEmail().equals(emailAddress) && model.getPassword().equals(password) && model.getContact().equals(contact)){
                                        if(user.isEmailVerified()){
                                            sharedPreference.saveData(getApplicationContext(), userStatus, emailAddress, password, true);
                                            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                                            finish();
                                        }else{
                                            Toast.makeText(LogIn.this, "Verify your email First", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(LogIn.this, "Please check your credentials", Toast.LENGTH_SHORT).show();
                                    }
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
}