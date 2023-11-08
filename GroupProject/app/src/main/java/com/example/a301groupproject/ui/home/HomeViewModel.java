package com.example.a301groupproject.ui.home;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a301groupproject.factory.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        return items;
    }

    // add to database
    public void addItem(Item item, ArrayList<String> imageUris) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Map<String, Object> itemData = new HashMap<>();

        itemData.put("name", item.getName());
        itemData.put("model", item.getModel());
        itemData.put("make", item.getMake());
        itemData.put("date", item.getDate());
        itemData.put("serialNumber",item.getSerialNumber());
        itemData.put("value", item.getValue());
        itemData.put("images", imageUris);
        itemData.put("tags",item.getTags());

        if (user != null) {
            String uid = user.getUid();

            // TODO: implement successful and fail message
            db.collection("users").document(uid).collection("items").document().set(itemData);
        }
    }

    /**
     * Remove an item in the Firestore database with the provided data.
     *
     * @param item The item to be remove by identify its unique id in the Firebase
     */
    public void removeItem(Item item) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getUid()).collection("items").document(item.getId()).delete();
    }
    /**
     * Edits an item in the Firestore database with the provided data.
     *
     * @param item       The item to be edited, containing updated information.
     * @param imageUris  The list of image URIs associated with the item.
     */
    public void editItem(Item item,ArrayList<String> imageUris) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference itemRef = db.collection("users").document(user.getUid()).collection("items").document(item.getId());
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", item.getName());
        updatedData.put("model", item.getModel());
        updatedData.put("make", item.getMake());
        updatedData.put("date", item.getDate());
        updatedData.put("value", item.getValue());
        updatedData.put("images", imageUris);
        itemRef.set(updatedData);
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
