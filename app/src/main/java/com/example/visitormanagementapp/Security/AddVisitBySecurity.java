package com.example.visitormanagementapp.Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.databinding.ActivityAddVisitBySecurityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddVisitBySecurity extends AppCompatActivity {

    ActivityAddVisitBySecurityBinding binding;

    String vehicleResponse, assetsResponse, imageUrl = " ", idBack, id;

    String hostResponse, meetingResponse, timeResponse, departmentResponse, otpResponse;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    List<String> nameList = new ArrayList<>();
    List<String> departmentList = new ArrayList<>();

    HashMap<String, Object> map  = new HashMap<>();

    private AlertDialog dialog;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_visit_by_security);

        handleClickEvents();


        fillHostSpinner();

        fillTimeSpinner();

        fillMeetingSpinner();

        progressDialog = new ProgressDialog(this);

    }

    private void handleClickEvents() {

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });

        binding.hostLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hostResponse = parent.getSelectedItem().toString();
                fillDepartmentSpinner(hostResponse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.departmentLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentResponse = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.appointmentLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeResponse = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.meetingLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meetingResponse = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void checkDetails() {
        boolean isValid = true;
        if (TextUtils.isEmpty(binding.contact.getText().toString())) {
            binding.contactLayout.setError("Please enter your contact number");
            isValid = false;
        } else {
            if (binding.contact.getText().toString().length() != 10) {
                binding.contactLayout.setError("Please enter valid contact number");
                isValid = false;
            }
        }
        if (TextUtils.isEmpty(binding.visitor.getText().toString())) {
            binding.visitorLayout.setError("Please enter visitor's name");
            isValid = false;
        }
        if (TextUtils.isEmpty(binding.company.getText().toString())) {
            binding.companyLayout.setError("Please enter visitor's company name");
            isValid = false;
        }
        if (TextUtils.isEmpty(binding.place.getText().toString())) {
            binding.placeLayout.setError("Please enter visitor's place name");
            isValid = false;
        }
        if (hostResponse.equals(null)) {
            binding.host.setError("Please enter the employee's name");
            isValid = false;
        }
        if (departmentResponse.equals(null)) {
            binding.department.setError("Please enter employee's department");
            isValid = false;
        }
        if (timeResponse.equals(null)) {
            binding.appointment.setError("Please enter meeting time");
            isValid = false;
        }
        if (meetingResponse.equals(null)) {
            binding.meeting.setError("Please enter meeting purpose");
            isValid = false;
        }
        if (binding.vehicleYes.isChecked()) {
            vehicleResponse = "Yes";
        } else if (binding.vehicleNo.isChecked()) {
            vehicleResponse = "No";
        } else {
            binding.vehicle.setError("Please select any one");
            isValid = false;
        }
        if (binding.assetsYes.isChecked()) {
            assetsResponse = "Yes";
        } else if (binding.assetsNo.isChecked()) {
            assetsResponse = "No";
        } else {
            binding.assets.setError("Please select any one");
            isValid = false;
        }

        if (isValid) {

            progressDialog.setTitle("Verifying Contact");
            progressDialog.setMessage("Please wait while we are verifying your contact");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            map.clear();
            map.put("visitorContact", binding.contact.getText().toString());
            map.put("visitorName", binding.visitor.getText().toString());
            map.put("visitorCompany", binding.company.getText().toString());
            map.put("visitorPlace", binding.place.getText().toString());
            map.put("hostName", hostResponse);
            map.put("hostDepartment", departmentResponse);
            map.put("meetingTime", timeResponse);
            map.put("meetingPurpose", meetingResponse);
            map.put("vehicle", vehicleResponse);
            map.put("assets", assetsResponse);
            map.put("visitorImage", imageUrl);
            map.put("request", "pending");
            map.put("active", true);
            map.put("nameCompany", binding.visitor.getText().toString()+binding.company.getText().toString());
            map.put("hostNameDepartment", hostResponse+departmentResponse);

           sendOtp(binding.contact.getText().toString());

            //addVisit(map);
        }
    }

    private void addVisit(HashMap<String, Object> map) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("ADD VISIT").child(binding.contact.getText().toString()).exists())){
                    databaseReference.child("ADD VISIT").child(binding.contact.getText().toString()).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddVisitBySecurity.this, "Meeting Request Added", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(AddVisitBySecurity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                    startActivity(new Intent(getApplicationContext(), SecurityHomeScreen.class));
                                    finish();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillHostSpinner() {

        databaseReference.child("VMS EMPLOYEE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String name = snapshot1.child("name").getValue(String.class);
                    nameList.add(name);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nameList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.hostLayout.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillMeetingSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.meetingPurpose, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.meetingLayout.setAdapter(adapter);

    }

    private void fillTimeSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.appointmentLayout.setAdapter(adapter);

    }

    private void fillDepartmentSpinner(String hostResponse) {
        departmentList.clear();
        databaseReference.child("VMS EMPLOYEE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String name = snapshot1.child("name").getValue(String.class);
                    if(name.equals(hostResponse)){
                        String dept = snapshot1.child("department").getValue(String.class);
                        if(!(departmentList.contains(dept))){
                            departmentList.add(dept);
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, departmentList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.departmentLayout.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendOtp(String contact) {

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d("OTP", "onVerificationCompleted:" + phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("OTPf", "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d("OTPc", "onCodeSent:" + s);
                progressDialog.dismiss();
                dialogShow(s);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + contact)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }



    private void dialogShow(String contact) {
        if(dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(AddVisitBySecurity.this);
            View view= LayoutInflater.from(this).inflate(R.layout.dialog_box, (ViewGroup) findViewById(R.id.otp_relative));
            builder.setView(view);
            dialog=builder.create();
            final EditText otpText = view.findViewById(R.id.inputOtp);
            if(dialog.getWindow()!=null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.show();
            }


            view.findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userOtp = otpText.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(contact, userOtp);

                    FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                    mAuth1.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.setTitle("Adding Request");
                                progressDialog.setMessage("Please wait while we are adding your request");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                addVisit(map);
                                mAuth1.signOut();
                                dialog.dismiss();
                                dialog = null;
                            }else{
                                Toast.makeText(AddVisitBySecurity.this, "Invalid", Toast.LENGTH_SHORT).show();
                                otpText.setError("Please enter correct otp");
                            }
                        }
                    });

                }
            });

            view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog=null;
                }
            });
        }
    }



}