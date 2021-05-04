package com.example.visitormanagementapp.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.visitormanagementapp.Adapters.VisitHolder;
import com.example.visitormanagementapp.Models.VisitAddModel;
import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Registration.RegistrationActivity;
import com.example.visitormanagementapp.Security.AddVisitBySecurity;
import com.example.visitormanagementapp.Security.CheckVisitBySecurity;
import com.example.visitormanagementapp.databinding.ActivityHomeScreenEmployeeBinding;
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

public class HomeScreen extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    ActivityHomeScreenEmployeeBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Query query;
    FirebaseRecyclerAdapter adapter;
    FirebaseRecyclerOptions options;

    String name, department;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen_employee);

        setSupportActionBar(binding.toolbar);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.signOut) {
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

        Log.d("Start", getIntent().getStringExtra("Email"));

        databaseReference.child("VMS EMPLOYEE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String email = dataSnapshot.child("email").getValue(String.class);
                    Log.d("email", email);
                    if(email.equals(getIntent().getStringExtra("Email"))){
                        name = dataSnapshot.child("name").getValue(String.class);
                        department = dataSnapshot.child("department").getValue(String.class);
                        break;
                    }
                }

                Log.d("NE", name + department);

                query = databaseReference.child("ADD VISIT").orderByChild("hostNameDepartment").equalTo(name + department);
                Log.d("N", "Query");

                options = new FirebaseRecyclerOptions.Builder<VisitAddModel>()
                        .setQuery(query, VisitAddModel.class)
                        .build();

                adapter = new FirebaseRecyclerAdapter<VisitAddModel, VisitHolder>(options) {
                    @NonNull
                    @Override
                    public VisitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_for_employee_home, parent, false);
                       return new VisitHolder(v);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull VisitHolder holder, int position, @NonNull VisitAddModel model) {
                        Log.d("C", model.getVisitorContact());
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
                            holder.layout.setVisibility(View.GONE);
                            holder.stateImage.setImageResource(R.color.green);
                        } else {
                            holder.layout.setVisibility(View.GONE);
                            holder.stateImage.setImageResource(R.color.red);
                        }

                        if (model.isActive()) {
                            holder.activeImage.setImageResource(R.color.green);
                        } else {
                            holder.activeImage.setImageResource(R.color.red);
                        }

                        holder.accept_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateChange("approved", model.getVisitorContact());
                            }
                        });

                        holder.decline_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateChange("decline", model.getVisitorContact());
                            }
                        });


                    }
                };

                binding.recyclerView.setAdapter(adapter);
                adapter.startListening();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateChange(String approved, String contact) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("ADD VISIT").child(contact).exists()){
                    databaseReference.child("ADD VISIT").child(contact).child("request").setValue(approved);
                    if(approved.equals("decline")){
                        databaseReference.child("ADD VISIT").child(contact).child("active").setValue(false);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}