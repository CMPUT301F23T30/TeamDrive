package com.example.a301groupproject.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mUsername;
    private MutableLiveData<String> mUserEmail;

    public GalleryViewModel() {
        mUsername = new MutableLiveData<>();
        mUserEmail = new MutableLiveData<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String username = task.getResult().getString("username");
                String userEmail = task.getResult().getString("email"); // replace "email" with your actual field name

                mUsername.setValue(username);
                mUserEmail.setValue(userEmail);
            } else {

            }
        });
    }

    public LiveData<String> getUsername() {
        return mUsername;
    }

    public LiveData<String> getUserEmail() {
        return mUserEmail;
    }


}