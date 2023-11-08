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


    public void addItem(Item item, Uri imageUris) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Map<String, Object> itemData = new HashMap<>();

        itemData.put("name", item.getName());
        itemData.put("model", item.getModel());
        itemData.put("make", item.getMake());
        itemData.put("date", item.getDate());
        itemData.put("value", item.getValue());
        itemData.put("images", imageUris);
        itemData.put("Tags",item.getTags());

        if (user != null) {
            String uid = user.getUid();

            // TODO: implement successful and fail message
            db.collection("users").document(uid).collection("items").document().set(itemData);

        }
    }

    public void removeItem(Item item) {

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getUid()).collection("items").document(item.getId()).delete();

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
