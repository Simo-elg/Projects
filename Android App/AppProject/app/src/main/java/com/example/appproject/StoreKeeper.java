package com.example.appproject;
import android.content.Context;

import java.util.ArrayList;

public class StoreKeeper extends User {
    //Initialisation of specific StoreKeeper attributes
    boolean created = false ; // helps us know if the user has already signed up "once"
    ArrayList<Component> stock; // the Storekeeper has direct access to the stock

    // Constructor
    StoreKeeper(Context context, String mail, String passcode, String fname, String lname, String StoreKeeper){
        super(context,mail,passcode,fname,lname,"StoreKeeper");
        stock = App.stock;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public ArrayList<Component> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Component> stock) {
        this.stock = stock;
    }

    // Session interface implementation
    public void logIn(String email,String password){};
    public void logOut(){};
    public boolean signUp(String first_name, String last_name ,String email, String password){
        if (created){
            return false; // a user that already signed up, can't do it again
        }
        return true;
    }

    //Staff interface : not yet implemented
    public void manage(){};

    //regular methods : not yet implemented
    private void addComponent(Component c){

    }
    private void deleteComponent(Component c){}
    private void consultComponent(Component c){}
    private boolean isInStock(Component c){return true;}


}
