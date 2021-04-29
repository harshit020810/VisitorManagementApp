package com.example.visitormanagementapp.SharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SharedPreference {

    SharedPreferences sharedPreferencesSecurity,sharedPreferencesEmployee;

    public SharedPreference() {
    }

    public void saveData(Context context, String userStatus, String email, String password, Boolean isActive){
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesSecurity.edit();
            editor.putString("Email", email);
            editor.putString("Password", password);
            editor.putBoolean("isActive", isActive);
            editor.apply();
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesEmployee.edit();
            editor.putString("Email", email);
            editor.putString("Password", password);
            editor.putBoolean("isActive", isActive);
            editor.apply();
        }
    }

    public boolean readData(Context context, String userStatus, String email, String password, Boolean isActive){
        String actualEmail, actualPassword;
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            actualEmail = sharedPreferencesSecurity.getString("Email","");
            actualPassword = sharedPreferencesSecurity.getString("Password","");
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            actualEmail = sharedPreferencesEmployee.getString("Identification Number","");
            actualPassword = sharedPreferencesEmployee.getString("Password","");
        }

        return email.equals(actualEmail) && password.equals(actualPassword);
    }

    public boolean checkSessionFromEmployee(Context context){

        sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
        return sharedPreferencesEmployee.getBoolean("isActive",false);
    }


    public List<String> retrieveInformation(Context context, String userStatus){
        List<String> retrieveList = new ArrayList<>();
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            retrieveList.add(sharedPreferencesSecurity.getString("Identification Number", ""));
            retrieveList.add(sharedPreferencesSecurity.getString("Password", ""));
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee" ,Context.MODE_PRIVATE);
            retrieveList.add(sharedPreferencesEmployee.getString("Identification Number", ""));
            retrieveList.add(sharedPreferencesEmployee.getString("Password", ""));
        }

        return retrieveList;

    }






}
