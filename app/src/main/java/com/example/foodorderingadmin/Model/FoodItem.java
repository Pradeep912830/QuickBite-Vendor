package com.example.foodorderingadmin.Model;

public class FoodItem {
    private String name;
    private String description; // you called it source earlier
    private String price;
    private String imageUrl;
    private int quantity;
    private String key;

    public FoodItem() {
        // Needed for Firebase
    }

    public FoodItem(String name, String description, String price, String imageUrl, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
