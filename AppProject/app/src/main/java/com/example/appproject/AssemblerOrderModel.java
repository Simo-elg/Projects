package com.example.appproject;

public class AssemblerOrderModel {
    private int orderId;
    private int clientId;
    private String component;
    private String status;

    public AssemblerOrderModel(int orderId,String status){
        this.orderId = orderId;
        //this.clientId = clientId;
        //this.component = component;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }
    public String getStatus(){
        return status;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
