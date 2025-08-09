package com.example.foodorderingadmin.Model;

public class AdminUser {
    public String ownerName;
    public String restaurantName;
    public String email;
    public String location;

    // Needed for Firebase to deserialize the object
    public AdminUser() {}

    public AdminUser(String ownerName, String restaurantName, String email, String location) {
        this.ownerName = ownerName;
        this.restaurantName = restaurantName;
        this.email = email;
        this.location = location;
    }
}
