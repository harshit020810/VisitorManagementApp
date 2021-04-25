package com.example.visitormanagementapp.Models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VISIT")
public class RoomEntityForVisit {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String contact;
    private String visitorName;
    private String companyName;
    private String visitorPlace;
    private String host;
    private String department;
    private String meetingPurpose;
    private String appointment;
    private String vehicle;
    private String assets;
    private String imageUrl;
    private String identificationNumber;
    private boolean isActive;
    private String state;

    public RoomEntityForVisit() {
    }

    public RoomEntityForVisit(String contact, String visitorName, String companyName, String visitorPlace, String host,
                              String department, String meetingPurpose, String appointment, String vehicle, String assets, String imageUrl,
                              String identificationNumber, boolean isActive, String state) {
        this.contact = contact;
        this.visitorName = visitorName;
        this.companyName = companyName;
        this.visitorPlace = visitorPlace;
        this.host = host;
        this.department = department;
        this.meetingPurpose = meetingPurpose;
        this.appointment = appointment;
        this.vehicle = vehicle;
        this.assets = assets;
        this.imageUrl = imageUrl;
        this.identificationNumber = identificationNumber;
        this.isActive = isActive;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVisitorPlace() {
        return visitorPlace;
    }

    public void setVisitorPlace(String visitorPlace) {
        this.visitorPlace = visitorPlace;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMeetingPurpose() {
        return meetingPurpose;
    }

    public void setMeetingPurpose(String meetingPurpose) {
        this.meetingPurpose = meetingPurpose;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}
