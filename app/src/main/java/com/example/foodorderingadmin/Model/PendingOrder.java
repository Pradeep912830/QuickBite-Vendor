package com.example.foodorderingadmin.Model;

public class PendingOrder {
    private String customerName;
    private int quantity;
    private int foodImageResId;
    private String actionText;

    public PendingOrder(String customerName, int quantity, int foodImageResId, String actionText) {
        this.customerName = customerName;
        this.quantity = quantity;
        this.foodImageResId = foodImageResId;
        this.actionText = actionText;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFoodImageResId() {
        return foodImageResId;
    }

    public String getActionText() {
        return actionText;
    }
}
