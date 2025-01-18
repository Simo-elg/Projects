package com.example.appproject;

public class HardwareComponent extends Component{
    String voltage;
    String wattage;
    String dimensions;
    HardwareComponent(String type, String sous_type, String description, int quantite, String price, String V,String W,String dim){
        super(type, sous_type, description, quantite, price);
        voltage = V;
        wattage = W;
        dimensions = dim;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getWattage() {
        return wattage;
    }

    public void setWattage(String wattage) {
        this.wattage = wattage;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

}
