package com.example.foodorderingadmin.Model;

public class PendingOrder {
    private String foodName;
    private String foodImage;
    private int foodPrice;
    private String orderId;
    private String userId;
    private String pendingButton;
    private int foodQuantity;
    private String itemId;

    public PendingOrder(String foodImage, String foodName, int foodPrice, int foodQuantity, String pendingButton, String orderId, String userId, String itemId) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.pendingButton = pendingButton;
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
