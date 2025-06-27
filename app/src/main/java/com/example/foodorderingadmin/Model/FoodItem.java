package com.example.foodorderingadmin.Model;

public class FoodItem {
    private int imageResId;
    private String name;
    private String source;
    private String price;
    private int quantity;

    public FoodItem(int imageResId, String name, String source, String price, int quantity) {
        this.imageResId = imageResId;
        this.name = name;
        this.source = source;
        this.price = price;
        this.quantity = quantity;
    }


    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

}
