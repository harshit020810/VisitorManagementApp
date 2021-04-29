package com.example.visitormanagementapp.Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.databinding.ActivityHomeScreenSecurityBinding;

import java.util.List;

public class SecurityHomeScreen extends AppCompatActivity {

    ActivityHomeScreenSecurityBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen_security);

        setSupportActionBar(binding.toolbar);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.addVisit){
            startActivity(new Intent(getApplicationContext(), AddVisitBySecurity.class));
            return true;
        }else if(id == R.id.checkVisit){
            startActivity(new Intent(getApplicationContext(), CheckVisitBySecurity.class));
            return true;
        }else if(id == R.id.signOut){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}