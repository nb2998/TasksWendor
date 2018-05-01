package com.example.nb2998.taskwendor.Models;

public class SingleItem {
    private String name, image_url;
    private int tot_units, left_units;
    private int price;

    public SingleItem(String name, String image_url, int tot_units, int left_units, int price) {
        this.name = name;
        this.image_url = image_url;
        this.tot_units = tot_units;
        this.left_units = left_units;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getTot_units() {
        return tot_units;
    }

    public int getLeft_units() {
        return left_units;
    }

    public float getPrice() {
        return price;
    }
}
