package com.example.foodorderingadmin.Model;

public class OrderModel {
    String userId;
    String orderId;
    String foodName;
    String address;
    String imageUrl;
    String status;
    String customerName;
    int singlePrice;
    int totalPrice;
    int totalQuantity;
    String phone;

    public OrderModel(String userId, String orderId, String foodName, String address, String imageUrl, String status, String customerName, int singlePrice, int totalPrice, int totalQuantity, String phone) {
        this.userId = userId;
        this.orderId = orderId;
        this.foodName = foodName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.status = status;
        this.customerName = customerName;
        this.singlePrice = singlePrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(int singlePrice) {
        this.singlePrice = singlePrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
