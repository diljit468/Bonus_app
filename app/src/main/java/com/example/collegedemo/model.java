package com.example.collegedemo;

public class model {
    int id;
    String name;
    int price,time;

    public model(int id,String name, int price, int time) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getTime() {
        return time;
    }
}
