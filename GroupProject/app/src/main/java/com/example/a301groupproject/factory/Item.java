package com.example.a301groupproject.factory;

import java.io.Serializable;

public class Item implements Serializable {

    private String name;
    private String model;
    private String make;
    private String date;
    private String value;

    public Item(String name, String model, String make, String date, String value) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.date = date;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
