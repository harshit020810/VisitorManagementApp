package com.example.visitormanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.visitormanagementapp.Employee.HomeScreen;
import com.example.visitormanagementapp.Registration.RegistrationActivity;
import com.example.visitormanagementapp.Security.SecurityHomeScreen;
import com.example.visitormanagementapp.SharedPrefrence.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser user;
    SharedPreference sharedPreference = new SharedPreference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        user = mAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user == null){
                    startActivity(new Intent(SplashScreen.this, RegistrationActivity.class));
                }else{
                    if(sharedPreference.checkSessionFromEmployee(getApplicationContext())){
                        startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                    }else{
                        startActivity(new Intent(SplashScreen.this, SecurityHomeScreen.class));
                    }
                }
                finish();
            }
        }, 3000);
    }
}