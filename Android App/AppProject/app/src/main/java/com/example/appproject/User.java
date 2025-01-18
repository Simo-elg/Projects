package com.example.appproject;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

abstract class User extends AppCompatActivity {
    // Initialisation of the Attributes
    int ID;
    String userType;
    String email;
    String password;

    String firstname;
    String lastname;

    String date_of_creation;
    String date_of_modification;

    Context context;

    DDB databasemanager = new DDB(this);

    // keeping track of the number of users so we'll know the id we should give to a new user
    static int nbr_users = 0 ; //has to be imported from the DATABASE

    User(Context context, String mail, String passcode, String fname, String lname, String type){
        this.context = context;
        this.ID = nbr_users;
        this.nbr_users += 1;
        this.email = mail;
        this.password = passcode;
        this.firstname = fname;
        this.lastname = lname;
        this.userType = type;

        //Setting up the dates
        //at first the creation and modification date are the same
        Date currentDate = new Date();
        date_of_creation = currentDate.toString();
        date_of_modification = currentDate.toString();
    }


    //Getters
    public int getId(){
        return ID;
    }
    public String getEmail(){
        return email;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getPassword(){ return password; }
    public String getUserType(){ return userType; }
    public String getCreationDate(){
        return date_of_creation;
    }
    public String getModificationDate(){
        return date_of_modification;
    }

    //Setters
    public void setPassword(String passcode){
        password = passcode;
    }
    public void setEmail(String mail){
        email = mail;
    }
    public void setFirstname(String new_f_name){
        firstname = new_f_name;
    }
    public void setLastname(String new_l_name){
        lastname = new_l_name;
    }

    public void updateTime(){
        Date currentDate = new Date();
        date_of_modification = currentDate.toString();
    }

}
