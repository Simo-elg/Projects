package com.example.appproject;

import android.content.Context;

import java.util.ArrayList;

public class Administrator extends User {

    ArrayList<Requester> clients;
    String specialStaffPassCode = "47";

    //constructor
    public Administrator(Context context, String mail, String passcode, String fname, String lname, String administrator){
        super(context,mail,passcode,fname,lname, "Administrator");
         clients = App.clients;;
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getFirstname() {
        return super.getFirstname();
    }

    @Override
    public String getLastname() {
        return super.getLastname();
    }

    @Override
    public void setEmail(String mail) {
        super.setEmail(mail);
    }

    @Override
    public void setLastname(String new_l_name) {
        super.setLastname(new_l_name);
    }

    @Override
    public void setPassword(String passcode) {
        super.setPassword(passcode);
    }

    @Override
    public void setFirstname(String new_f_name) {
        super.setFirstname(new_f_name);
    }


}
