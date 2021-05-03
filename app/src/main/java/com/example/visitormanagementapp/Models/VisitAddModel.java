package com.example.visitormanagementapp.Models;

public class VisitAddModel {

    private String request, visitorImage, visitorPlace, visitorName, visitorCompany, hostName, hostDepartment, vehicle, assets, contact, meetingPurpose, meetingTime, nameCompany;
    private boolean active;

    public VisitAddModel() {
    }

    public VisitAddModel(String request, String visitorImage, String visitorPlace, String visitorName, String visitorCompany, String hostName,
                         String hostDepartment, String vehicle, String assets, String contact, String meetingPurpose, String meetingTime, String nameCompany, Boolean active) {
        this.request = request;
        this.visitorImage = visitorImage;
        this.visitorPlace = visitorPlace;
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
        this.hostName = hostName;
        this.hostDepartment = hostDepartment;
        this.vehicle = vehicle;
        this.assets = assets;
        this.contact = contact;
        this.meetingPurpose = meetingPurpose;
        this.meetingTime = meetingTime;
        this.nameCompany = nameCompany;
        this.active = active;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getVisitorImage() {
        return visitorImage;
    }

    public void setVisitorImage(String visitorImage) {
        this.visitorImage = visitorImage;
    }

    public String getVisitorPlace() {
        return visitorPlace;
    }

    public void setVisitorPlace(String visitorPlace) {
        this.visitorPlace = visitorPlace;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostDepartment() {
        return hostDepartment;
    }

    public void setHostDepartment(String hostDepartment) {
        this.hostDepartment = hostDepartment;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMeetingPurpose() {
        return meetingPurpose;
    }

    public void setMeetingPurpose(String meetingPurpose) {
        this.meetingPurpose = meetingPurpose;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }
}
