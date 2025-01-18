package com.example.appproject;
public class CartModel {

    private String productId;
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productImage;
    private String quanti;
    private String sub_t;

    public CartModel(String productId, String productName, String productPrice, String productDescription, String productImage,String quantity, String subtype) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.quanti = quantity;
        this.sub_t = subtype;

    }

    // Getter methods
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImage() {
        return productImage;
    }
    public String getQuantity(){
        return quanti;
    }
    public String getSub_t(){
        return sub_t;
    }
}