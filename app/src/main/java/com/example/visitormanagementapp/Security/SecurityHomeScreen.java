package com.example.visitormanagementapp.Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitormanagementapp.Adapters.VisitHolder;
import com.example.visitormanagementapp.Interfaces.ItemClickListener;
import com.example.visitormanagementapp.Models.VisitAddModel;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Registration.RegistrationActivity;
import com.example.visitormanagementapp.databinding.ActivityHomeScreenSecurityBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecurityHomeScreen extends AppCompatActivity {

    ActivityHomeScreenSecurityBinding binding;
    SharedPreferences sharedPreferences;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseRecyclerOptions<VisitAddModel> options;
    FirebaseRecyclerAdapter<VisitAddModel, VisitHolder> adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ADD VISIT");

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen_security);

        setSupportActionBar(binding.toolbar);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading Info");
        progressDialog.setMessage("Please wait while we are loading the details");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.addVisit) {
            startActivity(new Intent(getApplicationContext(), AddVisitBySecurity.class));
            return true;
        } else if (id == R.id.checkVisit) {
            startActivity(new Intent(getApplicationContext(), CheckVisit.class));
            return true;
        } else if (id == R.id.signOut) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        options = new FirebaseRecyclerOptions.Builder<VisitAddModel>()
                .setQuery(databaseReference, VisitAddModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<VisitAddModel, VisitHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull VisitHolder holder, int position, @NonNull VisitAddModel model) {
                if(model.getMeetingTime().charAt(0) == '1' && (model.getMeetingTime().charAt(1) == '0' || model.getMeetingTime().charAt(1) == '1')){
                    holder.timeResponse.setText(model.getMeetingTime() + " AM");
                }else{
                    holder.timeResponse.setText(model.getMeetingTime() + " PM");
                }
                holder.visitorNameResponse.setText(model.getVisitorName());
                holder.visitorCompanyResponse.setText(model.getVisitorCompany());
                holder.hostNameResponse.setText(model.getHostName());
                holder.hostDepartmentResponse.setText(model.getHostDepartment());

                if(model.getRequest().equals("pending")){
                    holder.stateImage.setImageResource(R.color.yellow);
                }else if(model.getRequest().equals("approved")){
                    holder.stateImage.setImageResource(R.color.green);
                }else{
                    holder.stateImage.setImageResource(R.color.red);
                }

                if(model.isActive()){
                    holder.activeImage.setImageResource(R.color.green);
                }else{
                    holder.activeImage.setImageResource(R.color.red);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DetailVisit.class);
                        intent.putExtra("Contact", model.getVisitorContact());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public VisitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_security_home, parent, false);
                return new VisitHolder(v);
            }
        };

        binding.recyclerView.setAdapter(adapter);
        adapter.startListening();
        progressDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}