package com.example.appproject;

public class SoftwareComponent extends Component{
    String version;
    String license;
    String compatibility;


    SoftwareComponent(String type1, String subType1, String description1, int quantite, String price, String version, String license, String compatibility) {
        super(type1, subType1, description1, quantite, price);
        this.version = version;
        this.license = license;
        this.compatibility = compatibility;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }
}
