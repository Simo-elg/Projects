package com.example.appproject;

public class OrderModel { //new
    private int orderId;
    private int clientId;
    private String state;
    private String message;
    private String components;

    public OrderModel(int orderId, int clientId, String state, String message, String components) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.state = state;
        this.message = message;
        this.components = components;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getComponents() {
        return components;
    }
}