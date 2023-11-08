package com.example.a301groupproject.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a301groupproject.factory.Item;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<ArrayList<Item>> getItems() {
        return items;
    }

    public void addItem(Item item) {
        ArrayList<Item> itemsValue = items.getValue();
        if (item != null) {
            itemsValue.add(item);
            items.setValue(itemsValue);
        }
    }

    public void removeItem(Item item) {
        ArrayList<Item> itemsValue = items.getValue();
        if (item != null) {
            itemsValue.remove(item);
            items.setValue(itemsValue);
        }
    }

    public double calculateTotalValue() {
        double total = 0.0;
        ArrayList<Item> itemsList = items.getValue();
        if (itemsList != null) {
            for (Item item : itemsList) {
                try {
                    total += Double.parseDouble(item.getValue());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return total;
    }

}
