package com.example.foodorderingadmin.Model;

public class PendingOrder {
    private String foodName;
    private String foodImage;
    private int foodPrice;
    private String pendingButton;
    private int foodQuantity;

    public PendingOrder(String foodImage, String foodName, int foodPrice, int foodQuantity, String pendingButton) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.pendingButton = pendingButton;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getPendingButton() {
        return pendingButton;
    }

    public void setPendingButton(String pendingButton) {
        this.pendingButton = pendingButton;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }
}
