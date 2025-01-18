package com.example.appproject;

public class Stock extends Component{

    Stock(int ide,String type1, String subType1, String description1, int quantite, String price) {
        super(type1, subType1, description1, quantite, price);
        this.id =ide;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantite;
    }
}
