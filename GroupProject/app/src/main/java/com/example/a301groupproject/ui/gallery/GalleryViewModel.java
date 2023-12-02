package com.example.a301groupproject.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;


public class GalleryViewModel extends ViewModel {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String email;

    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private MutableLiveData<String> UserName = new MutableLiveData<>();
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public GalleryViewModel() {
        fetchUserData();
    }

    public void fetchUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
            String uid = currentUser.getUid();

            Task<DocumentSnapshot> users = mFirestore.collection("users").document(email).get();
            users.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    setUsername(documentSnapshot.getString("username"));
                }
            });

        }
    }

    // String getters
    public String getUsername() {
        return username;
    }

    public String getUserEmail() {
        return email;
    }
}

