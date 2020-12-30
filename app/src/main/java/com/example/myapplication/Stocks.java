package com.example.myapplication;

public class Stocks {
    private int id;
    private String shareName;
    private Double sharePrice;
    private Integer shareOwned;
    public Stocks(String shareName, Double sharePrice,Integer sharesOwned) {
        this.shareName= shareName ;
        this.sharePrice = sharePrice;
        this.shareOwned = sharesOwned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public Integer getShareOwned() {
        return shareOwned;
    }

    public void setShareOwned(Integer shareOwned) {
        this.shareOwned = shareOwned;
    }
}