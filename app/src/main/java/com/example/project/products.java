package com.example.project;

public class products {
    String pName,pPrice,pQuantity, pOwner, pType;
    byte[] image;

    public products(String pName, String pPrice, String pQuantity, String pOwner, String pType, byte[] image) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pQuantity = pQuantity;
        this.pOwner = pOwner;
        this.pType = pType;
        this.image = image;
    }

    public String getpOwner() {
        return pOwner;
    }

    public void setpOwner(String pOwner) {
        this.pOwner = pOwner;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
