package com.example.a301groupproject.ui.home;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a301groupproject.factory.item.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ViewModel to manage UI-related data in the lifecycle of the HomeFragment. It offers an interface to interact with the Firestore database,
 * to add, remove, and edit items, and to manage image URIs for items. It abstracts the data management from the HomeFragment,
 * allowing for a separation of concerns and easier testing.
 */
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>(new ArrayList<>());

    private MutableLiveData<ArrayList<Uri>> images = new MutableLiveData<>(new ArrayList<>());

    private FirebaseFirestore db;
    private FirebaseUser user;
    /**
     * Retrieves the live data instance containing the list of image URIs.
     * @return The MutableLiveData object containing the list of image URIs.
     */
    public MutableLiveData<ArrayList<Uri>> getImages() {
        return images;
    }
    /**
     * Adds a new image URI to the existing list of image URIs in the MutableLiveData.
     * @param uri The new image URI to add to the list.
     */
    public void addImage(Uri uri) {
        ArrayList<Uri> imagesValue = images.getValue();
        imagesValue.add(uri);
        images.setValue(imagesValue);
    }
    /**
     * Clears the list of image URIs, setting an empty list in the MutableLiveData.
     */
    public void emptyImages() {
        images.setValue(new ArrayList<>());
    }
    /**
     * Gets the live data list of items. It initializes or updates the list of items from Firestore
     * when changes occur. It listens to the Firestore snapshot updates and keeps the items list updated.
     * @return The MutableLiveData object containing the current list of items.
     */
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
                                item.setId(doc.getId());
                                itemList.add(item);
                            }
                            items.setValue(itemList);
                        }
                    });
        }

        return items;
    }

    /**
     * Adds a new item to the Firestore database. It compiles the item data into a map and uploads it to the database.
     * @param item The item to add to the database.
     * @param imageUris The list of strings representing image URIs to associate with the item.
     */
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
        itemData.put("description",item.getDescription());
        itemData.put("comment",item.getComment());
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
        updatedData.put("serialNumber",item.getSerialNumber());
        updatedData.put("value", item.getValue());
        updatedData.put("description",item.getDescription());
        updatedData.put("comment",item.getComment());
        updatedData.put("images", imageUris);
        updatedData.put("tags",item.getTags());
        // Set the updated data in the Firestore document
        itemRef.set(updatedData);
    }
    public  MutableLiveData<ArrayList<Item>> getTheItems(){
        return this.items;
    }

    /**
     * Calculates the total value of all items by summing their individual values.
     * Handles NumberFormatException if a value is not a valid double.
     * @return The total value of all items as a double.
     */
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
    public void setItemsValue(ArrayList<Item> i){
        items.setValue(i);
    }


}
