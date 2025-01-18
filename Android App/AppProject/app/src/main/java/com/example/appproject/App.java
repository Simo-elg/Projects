package com.example.appproject;
import android.content.Context;

import java.util.ArrayList ;

public class App {
    //the app's DATABASE
    DDB database;

    // initialisation of multiple arrays of data that will be filled from our DATABASE
    // Thus giving us easy and direct access to our : stock, orders & clients
    static ArrayList<Component> stock;
    static ArrayList<Order> orders;
    static ArrayList<Requester> clients = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();
    Context context;

    Administrator admin = new Administrator(context, "admin@mypcfactory.com","1234","Sami","Amara", "Administrator");
    StoreKeeper storekeeper = new StoreKeeper(context,"storek@mypcfactory.com","0000","Simo","El Guella", "StoreKeeper");
    Assembler assembler = new Assembler(context,"assembler@mypcfactory.com","1111","Salma","Sajid", "Assembler");


}
