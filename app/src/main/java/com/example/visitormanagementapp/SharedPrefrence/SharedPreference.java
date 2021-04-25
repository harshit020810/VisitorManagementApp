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

    public void saveData(Context context, String userStatus, String identificationNumber, String password, Boolean isActive, Boolean extraAdded){
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesSecurity.edit();
            editor.putString("Identification Number", identificationNumber);
            editor.putString("Password", password);
            editor.putBoolean("isActive", isActive);
            editor.putBoolean("extraData", extraAdded);
            editor.apply();
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesEmployee.edit();
            editor.putString("Identification Number", identificationNumber);
            editor.putString("Password", password);
            editor.putBoolean("isActive", isActive);
            editor.putBoolean("extraData", extraAdded);
            editor.apply();
        }
    }

    public boolean readData(Context context, String userStatus, String identificationNumber, String password, Boolean isActive){
        String actualIdentificationNumber, actualPassword;
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            actualIdentificationNumber = sharedPreferencesSecurity.getString("Identification Number","");
            actualPassword = sharedPreferencesSecurity.getString("Password","");
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            actualIdentificationNumber = sharedPreferencesEmployee.getString("Identification Number","");
            actualPassword = sharedPreferencesEmployee.getString("Password","");
        }

        return identificationNumber.equals(actualIdentificationNumber) && password.equals(actualPassword);

    }

    public boolean checkSessionFromSecurity(Context context){

        sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
        return sharedPreferencesSecurity.getBoolean("isActive",false);
    }

    public boolean checkSessionFromEmployee(Context context){

        sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
        return sharedPreferencesEmployee.getBoolean("isActive",false);
    }

    public boolean addExtraInformation(Context context, String userStatus){
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            return sharedPreferencesSecurity.getBoolean("extraData", false);
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            return sharedPreferencesEmployee.getBoolean("extraData", false);
        }
    }

    public void setExtraInformation(Context context, String userStatus){
        if(userStatus.equals("Security")){
            sharedPreferencesSecurity = context.getSharedPreferences("Security",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesSecurity.edit();
            editor.putBoolean("extraData", true);
            editor.apply();
        }else{
            sharedPreferencesEmployee = context.getSharedPreferences("Employee",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesEmployee.edit();
            editor.putBoolean("extraData", true);
            editor.apply();
        }
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
