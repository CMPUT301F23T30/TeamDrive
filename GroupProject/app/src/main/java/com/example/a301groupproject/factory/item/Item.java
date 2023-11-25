package com.example.a301groupproject.factory.item;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private String name;
    private String model;
    private String make;
    private String date;
    private String serialNumber;
    private String value;
    private ArrayList<String> tags;
    private String description;
    private String comment;
    private String id;
    private ArrayList<String> images;
    private boolean isChecked;

    public Item(String name, String model, String make, String date, String value, String serialNumber, String description, String comment, ArrayList<String> tags) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.date = date;
        this.value = value;
        this.serialNumber = serialNumber;
        this.description = description;
        this.comment = comment;
        this.tags = tags;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getSerialNumber(){ return serialNumber;}
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

    public String getComment(){ return comment;}
    public void setComment(String comment){
        this.comment = comment;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public int getTagsSize(){return tags.size();}

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

}
