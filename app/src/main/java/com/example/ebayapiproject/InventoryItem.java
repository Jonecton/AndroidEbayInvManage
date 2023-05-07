package com.example.ebayapiproject;

public class InventoryItem {
    private int id;
    private String title;
    private String type;
    private String brand;
    private String size;
    private double cost;
    private double listPrice;

    public InventoryItem(int id, String title, String type, String brand, String size, double cost, double listPrice) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.brand = brand;
        this.size = size;
        this.cost = cost;
        this.listPrice = listPrice;
    }



    public void setTitle(String title) {this.title = title;
    }
    public String getTitle() {return title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }
}
