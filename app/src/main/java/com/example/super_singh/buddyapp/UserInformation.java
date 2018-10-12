package com.example.super_singh.buddyapp;

/**
 * Created by Super-Singh on 11-10-2018.
 */

public class UserInformation {
    public String FirstName;
    public String LastName;
    public String Email;
    public String GYear;
    public String University;

    public UserInformation(){

    }

    public UserInformation(String firstName, String lastName, String email, String GYear, String university) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.GYear = GYear;
        University = university;
    }
}
