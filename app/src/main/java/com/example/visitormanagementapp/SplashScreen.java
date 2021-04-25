package com.example.visitormanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.visitormanagementapp.Employee.HomeScreen;
import com.example.visitormanagementapp.Registration.FillDetailsActivity;
import com.example.visitormanagementapp.Registration.RegistrationActivity;
import com.example.visitormanagementapp.Security.SecurityHomeScreen;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;

public class SplashScreen extends AppCompatActivity {

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        sharedPreference = new SharedPreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreference.checkSessionFromSecurity(getApplicationContext())){
                    if(sharedPreference.addExtraInformation(getApplicationContext(), "Security")){
                        startActivity(new Intent(getApplicationContext(), SecurityHomeScreen.class));
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), FillDetailsActivity.class);
                        intent.putExtra("UserStatus", "Security");
                        startActivity(intent);
                        finish();
                    }

                }

                else if(sharedPreference.checkSessionFromEmployee(getApplicationContext())){
                    if(sharedPreference.addExtraInformation(getApplicationContext(), "Employee")){
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), FillDetailsActivity.class);
                        intent.putExtra("UserStatus", "Employee");
                        startActivity(intent);
                        finish();
                    }

                }
                else{
                    startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}