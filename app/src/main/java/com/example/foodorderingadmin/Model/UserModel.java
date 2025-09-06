package com.example.foodorderingadmin.Model;

public class UserModel {
    private String name;
    private String email;
    private String location;
    private String restaurant;

    public UserModel(){

    }
    public UserModel(String name, String email, String location, String restaurant) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.restaurant = restaurant;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
}
