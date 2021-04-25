package com.example.visitormanagementapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VMS_DETAILS")
public class RoomEntityForExtraInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String contact;

    private String department;

    private String identificationNumber;

    private String password;

    private String userStatus;

    private String imageUrl;

    public RoomEntityForExtraInfo() {
    }

    public RoomEntityForExtraInfo(String name, String contact, String department, String identificationNumber, String password, String userStatus, String imageUrl) {
        this.name = name;
        this.contact = contact;
        this.department = department;
        this.identificationNumber = identificationNumber;
        this.password = password;
        this.userStatus = userStatus;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
