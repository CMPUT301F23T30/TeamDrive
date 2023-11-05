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
}
