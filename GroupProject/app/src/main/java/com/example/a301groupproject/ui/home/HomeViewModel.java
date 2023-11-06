package com.example.a301groupproject.ui.home;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a301groupproject.factory.item.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>(new ArrayList<>());

    private MutableLiveData<ArrayList<Uri>> images = new MutableLiveData<>(new ArrayList<>());

    private FirebaseFirestore db;
    private FirebaseUser user;

    public MutableLiveData<ArrayList<Uri>> getImages() {
        return images;
    }

    public void addImage(Uri uri) {
        ArrayList<Uri> imagesValue = images.getValue();
        imagesValue.add(uri);
        images.setValue(imagesValue);
    }

    public void emptyImages() {
        images.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Item>> getItems() {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("users").document(uid).collection("items")
                    .addSnapshotListener((snapshots, e) -> {
                        ArrayList<Item> itemList = new ArrayList<>();
                        if (snapshots != null) {
                            for (DocumentSnapshot doc : snapshots) {
                                Item item = doc.toObject(Item.class);
                                itemList.add(item);
                            }
                            items.setValue(itemList);
                        }
                    });
        }

        return items;
    }

    // add to database
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

        if (user != null) {
            String uid = user.getUid();

            // TODO: implement successful and fail message
            db.collection("users").document(uid).collection("items").document().set(itemData);
        }
    }

    // TODO: need to be rewritten
    public void removeItem(Item item) {
        ArrayList<Item> itemsValue = items.getValue();
        if (item != null) {
            itemsValue.remove(item);
            items.setValue(itemsValue);
        }
    }
}
