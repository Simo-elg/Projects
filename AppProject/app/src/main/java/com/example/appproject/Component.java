package com.example.appproject;

import android.util.EventLogTags;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public abstract class Component extends AppCompatActivity {
    int id;
    static int nb_comp = 0;
    String type;
    String subType;
    String description;
    int quantite;
    String price;
    String date_of_creation;
    String date_of_modification;

    Component(String type1, String subType1, String description1, int quantite, String price){
        this.id  = nb_comp;
        nb_comp++;
        this.type = type1;
        this.subType = subType1;
        this.description = description1;
        this.quantite = quantite;
        this.price = price;
        Date currentDate = new Date();
        date_of_creation = currentDate.toString();
        date_of_modification = currentDate.toString();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSousType() {
        return subType;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantite(){
        return quantite;
    }

    public String getDescription() { return description; }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public String getDate_of_modification() {
        return date_of_modification;
    }

    public void setDate_of_modification(String date_of_modification) {
        this.date_of_modification = date_of_modification;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toString() {
        return "Component ID: " + id + ", Type: " + type + ", Sous-type: " + subType + ", Price: " + price;
    }
}
