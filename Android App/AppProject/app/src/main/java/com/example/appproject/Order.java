package com.example.appproject;
import java.util.ArrayList;
public class Order {
    // Initialisation of attributes
    static int nb_orders;
    private String orderId;

    ArrayList<Component> components;
    String state; // order's state

    String message;

    // Constructor
    public Order(){
        state = "awaiting acceptance";
        orderId = "0";
        nb_orders += 1;
        components = new ArrayList<>();
        message = "";
    }

    // regular methods :

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // other methods
    private void sendMessage(){}
    public void addComponent(Component c){components.add(c);}
    private void remove(Component c){}



}
