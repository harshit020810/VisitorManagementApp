package com.example.visitormanagementapp.Security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.visitormanagementapp.R;
import com.example.visitormanagementapp.Database.DatabaseRoomForExtra;
import com.example.visitormanagementapp.Interfaces.RoomDao;
import com.example.visitormanagementapp.Interfaces.RoomDaoForVisit;
import com.example.visitormanagementapp.Models.RoomEntityForVisit;
import com.example.visitormanagementapp.databinding.ActivityAddVisitBySecurityBinding;

import java.util.ArrayList;
import java.util.List;

public class AddVisitBySecurity extends AppCompatActivity {

    ActivityAddVisitBySecurityBinding binding;

    RoomEntityForVisit roomEntityForVisit;
    RoomDaoForVisit roomDaoForVisit;
    RoomDao roomDao;
    DatabaseRoomForExtra databaseRoomForExtra;

    String vehicleResponse, assetsResponse, imageUrl = " ", idBack, id;

    List<String> list;

    String hostResponse, meetingResponse, timeResponse, departmentResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_visit_by_security);

        handleClickEvents();

        list = new ArrayList<>();

        fillHostSpinner();

        fillDepartmentSpinner();

        fillTimeSpinner();

        fillMeetingSpinner();

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

            databaseRoomForExtra = DatabaseRoomForExtra.getDatabaseRoom(this);
            roomDao = databaseRoomForExtra.roomDao();
            roomDaoForVisit = databaseRoomForExtra.roomDaoForVisit();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    list = roomDao.getId(hostResponse, departmentResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roomEntityForVisit = new RoomEntityForVisit(binding.contact.getText().toString(), binding.visitor.getText().toString(), binding.company.getText().toString(),
                                    binding.place.getText().toString(), hostResponse, departmentResponse, meetingResponse, timeResponse, vehicleResponse, assetsResponse, imageUrl, list.get(0),
                                    true, "Processed");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    roomDaoForVisit.insertAllForVisit(roomEntityForVisit);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddVisitBySecurity.this, "Meeting Request Added", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                }
            }).start();


        }
    }

    private void fillHostSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Host, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.hostLayout.setAdapter(adapter);
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

    private void fillDepartmentSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Department, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.departmentLayout.setAdapter(adapter);
    }



}