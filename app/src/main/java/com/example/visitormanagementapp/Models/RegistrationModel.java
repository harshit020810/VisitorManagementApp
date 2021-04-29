package com.example.visitormanagementapp.Models;

public class RegistrationModel {

    String email, password, name, contact, department, image, id;

    public RegistrationModel() {
    }

    public RegistrationModel(String email, String password, String name, String contact, String department, String image, String id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.department = department;
        this.image = image;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getDepartment() {
        return department;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}
