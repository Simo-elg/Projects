package com.example.appproject;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class Requester extends User{
    //Initialisation of specific StoreKeeper attributes
    boolean created = false ;
    ArrayList<Component> cart;
    ArrayList<Order> orders;
    String dateCreation;

    Requester(Context context, String mail, String passcode, String fname, String lname, String Requester){
        super(context,mail,passcode, fname,lname,"Requester");
        cart = new ArrayList<Component>();
    }

    // Session interface implementation
    public void logIn(String email,String password){};
    public void logOut(){};
    public boolean signUp(String first_name, String last_name ,String email, String password){ // the user exists but hasn't sign up yet
        if (created){
            return false; // a user that already signed up, can't do it again
        }
        return true;
    }

    public boolean equals(Requester other){ //checking if there are two instances of the same user with just a different ID
        return this.email.equals(other.email) || this.password.equals(other.password) || this.lastname.equals(other.lastname) || this.firstname.equals(other.firstname);
    }


    //Staff interface : not yet implemented
    public void manage(){};

    //regular methods : not yet implemented
    private void CreateOrder(){}
    private String OrderState(){return "";}
    private Order deleteOrder(Order o){return null;}
    private void changeOrder(Order c){}

}
