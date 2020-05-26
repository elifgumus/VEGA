package com.example.project;

public class CardInfo {
    String pName, pPrice, seller, user;
    byte[] image;

    public CardInfo(String pName, String pPrice, String seller, String user, byte[] image) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.seller = seller;
        this.user = user;
        this.image = image;
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
