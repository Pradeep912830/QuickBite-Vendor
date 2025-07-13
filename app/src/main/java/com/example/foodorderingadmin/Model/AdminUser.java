package com.example.foodorderingadmin;

public class AdminUser {
    public String ownerName, restaurantName, email, location;

    public AdminUser() {
        // Required for Firebase
    }

    public AdminUser(String ownerName, String restaurantName, String email, String location) {
        this.ownerName = ownerName;
        this.restaurantName = restaurantName;
        this.email = email;
        this.location = location;
    }
}
