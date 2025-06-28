package com.example.foodorderingadmin.Model;

public class OrderItem {
    private String customerName;
    private String paymentType;
    private String status;

    public OrderItem(String customerName, String paymentType, String status) {
        this.customerName = customerName;
        this.paymentType = paymentType;
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getStatus() {
        return status;
    }
}
