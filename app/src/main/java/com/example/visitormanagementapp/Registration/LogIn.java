package com.example.visitormanagementapp.Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.visitormanagementapp.Employee.HomeScreen;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Security.SecurityHomeScreen;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.example.visitormanagementapp.databinding.ActivityLogInBinding;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding activityLogInBinding;
    String userStatus;
    SharedPreference sharedPreference;

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

        if(TextUtils.isEmpty(activityLogInBinding.identificationNumber.getText().toString())){
            activityLogInBinding.idLayout.setError("Please enter your identification number");
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


        if(activityLogInBinding.security.isChecked()){
            userStatus = activityLogInBinding.security.getText().toString();
        }else if(activityLogInBinding.employee.isChecked()){
            userStatus = activityLogInBinding.employee.getText().toString();
        }else{
            activityLogInBinding.security.setError("Please select one among the two");
            isValid = false;
        }


        if(isValid){
            String identificationNumber = activityLogInBinding.identificationNumber.getText().toString();
            String password = activityLogInBinding.password.getText().toString();

            if(sharedPreference.readData(this, userStatus, identificationNumber, password, true)){
                if(sharedPreference.addExtraInformation(this, userStatus)){
                    if(userStatus.equals("Security")){
                        startActivity(new Intent(getApplicationContext(), SecurityHomeScreen.class));
                    }else{
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    }
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), FillDetailsActivity.class);
                    intent.putExtra("UserStatus", userStatus);
                    startActivity(intent);
                    finish();
                }
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

        }

    }
}