package com.example.a301groupproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a301groupproject.ui.login.LoginFragment;
import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting. Login Activity is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI
     * This acts as a host like a container for the actual login fragment and signup fragments
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_login);
        FirebaseApp.initializeApp(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, loginFragment);
        fragmentTransaction.commit();
    }


}