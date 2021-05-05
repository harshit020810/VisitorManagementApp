package com.example.visitormanagementapp.Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.visitormanagementapp.Models.VisitAddModel;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.databinding.ActivityDetailVisitBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailVisit extends AppCompatActivity {

    ActivityDetailVisitBinding binding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    VisitAddModel model;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_visit);

        progressDialog = new ProgressDialog(this);

        fillDetails();
    }

    private void fillDetails() {

        progressDialog.setTitle("Fetching Details");
        progressDialog.setMessage("Please wait while we are fetching details");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                model = snapshot.child("ADD VISIT").child(getIntent().getStringExtra("Contact")).getValue(VisitAddModel.class);

                binding.visitorNameAnswer.setText(model.getVisitorName());
                binding.visitorCompanyAnswer.setText(model.getVisitorCompany());
                binding.visitorPlaceAnswer.setText(model.getVisitorPlace());
                binding.visitorContactAnswer.setText(model.getVisitorContact());


                binding.hostNameAnswer.setText(model.getHostName());
                binding.hostDepartmentAnswer.setText(model.getHostDepartment());

                binding.meetingPurposeAnswer.setText(model.getMeetingPurpose());
                if (model.getMeetingTime().charAt(0) == '1' && (model.getMeetingTime().charAt(1) == '0' || model.getMeetingTime().charAt(1) == '1')) {
                    binding.meetingTimeAnswer.setText(model.getMeetingTime() + " AM");
                } else {
                    binding.meetingTimeAnswer.setText(model.getMeetingTime() + " PM");
                }

                binding.vehicleAnswer.setText(model.getVehicle());
                binding.assetsAnswer.setText(model.getAssets());
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}