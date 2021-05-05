package com.example.visitormanagementapp.Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.visitormanagementapp.Adapters.VisitHolder;
import com.example.visitormanagementapp.Models.VisitAddModel;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.databinding.ActivityCheckVisitBySecurityBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CheckVisit extends AppCompatActivity {

    ActivityCheckVisitBySecurityBinding binding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<String> visitorName = new ArrayList<>();
    List<String> visitorCompany = new ArrayList<>();

    FirebaseRecyclerOptions<VisitAddModel> options;
    FirebaseRecyclerAdapter<VisitAddModel, VisitHolder> adapter;

    String visitorNameResponse, visitorCompanyResponse;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_visit_by_security);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fillUpNameView();

        fillUpCompanyView();

        handleClickEvents();

        progressDialog = new ProgressDialog(this);


    }

    private void fillUpNameView() {

        visitorName.clear();
        databaseReference.child("ADD VISIT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("visitorName").getValue(String.class);
                    if (!(visitorName.contains(name))) {
                        visitorName.add(name);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, visitorName);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.visitorName.setThreshold(1);

                binding.visitorName.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fillUpCompanyView() {
        visitorCompany.clear();
        databaseReference.child("ADD VISIT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String company = dataSnapshot.child("visitorCompany").getValue(String.class);
                    if (!(visitorCompany.contains(company))) {
                        visitorCompany.add(company);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, visitorCompany);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.visitorCompany.setThreshold(1);

                binding.visitorCompany.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void handleClickEvents() {
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {
        boolean isValid = true;
        if (TextUtils.isEmpty(binding.visitorName.getText().toString())) {
            binding.visitorName.setError("Please enter visitor's name");
            isValid = false;
        } else {
            visitorNameResponse = binding.visitorName.getText().toString();
        }

        if (TextUtils.isEmpty(binding.visitorCompany.getText().toString())) {
            binding.visitorCompany.setError("Please enter visitor's name");
            isValid = false;
        } else {
            visitorCompanyResponse = binding.visitorCompany.getText().toString();
        }

        if (isValid) {
            progressDialog.setTitle("Checking Request");
            progressDialog.setMessage("Please wait while we are checking your request");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Query query = FirebaseDatabase.getInstance().getReference("ADD VISIT").orderByChild("nameCompany").equalTo(visitorNameResponse + visitorCompanyResponse);
            setUpRecyclerView(query);
        }

    }

    private void setUpRecyclerView(Query query) {
        options = new FirebaseRecyclerOptions.Builder<VisitAddModel>()
                .setQuery(query, VisitAddModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<VisitAddModel, VisitHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull VisitHolder holder, int position, @NonNull VisitAddModel model) {
                if (model.getMeetingTime().charAt(0) == '1' && (model.getMeetingTime().charAt(1) == '0' || model.getMeetingTime().charAt(1) == '1')) {
                    holder.timeResponse.setText(model.getMeetingTime() + " AM");
                } else {
                    holder.timeResponse.setText(model.getMeetingTime() + " PM");
                }
                holder.visitorNameResponse.setText(model.getVisitorName());
                holder.visitorCompanyResponse.setText(model.getVisitorCompany());
                holder.hostNameResponse.setText(model.getHostName());
                holder.hostDepartmentResponse.setText(model.getHostDepartment());

                if (model.getRequest().equals("pending")) {
                    holder.stateImage.setImageResource(R.color.yellow);
                } else if (model.getRequest().equals("approved")) {
                    holder.stateImage.setImageResource(R.color.green);
                } else {
                    holder.stateImage.setImageResource(R.color.red);
                }

                if (model.isActive()) {
                    holder.activeImage.setImageResource(R.color.green);
                } else {
                    holder.activeImage.setImageResource(R.color.red);
                }
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