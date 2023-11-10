package com.example.a301groupproject.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a301groupproject.LoginActivity;
import com.example.a301groupproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;


    /**
     * Inflates the layout for this fragment, initializes FirebaseAuth and FirebaseFirestore instances,
     * and sets up click listeners for the sign-up process and navigation back to the login screen.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Returns the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        TextView backTextView = view.findViewById(R.id.textViewBack);
        backTextView.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) view.findViewById(R.id.editTextUserName)).getText().toString().trim();
                String email = ((EditText) view.findViewById(R.id.editTextEmail)).getText().toString().trim();
                String password = ((EditText) view.findViewById(R.id.editTextPassword)).getText().toString().trim();
                String reEnterPassword = ((EditText) view.findViewById(R.id.editTextReEnterPassword)).getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getActivity(), "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.equals(reEnterPassword)) {
                    mFirestore.collection("users").document(username).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    Toast.makeText(getActivity(), "Username already exists", Toast.LENGTH_SHORT).show();


                                } else {
                                    createUserWithEmailAndUsername(email, password, username);
                                    getParentFragmentManager().beginTransaction()
                                            .replace(R.id.fragment_container, new LoginFragment())
                                            .addToBackStack(null)
                                            .commit();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Error checking for username", Toast.LENGTH_SHORT).show();
                                Log.e("SignUpError", "Error checking username", e);                            });
                } else {
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /**
     * Checks if the email entered by the user is valid.
     *
     * @param email The email CharSequence to validate.
     * @return Returns true if the email is not empty and matches the email pattern, false otherwise.
     */
    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Attempts to create a new user with the specified email and password, and then calls
     * {@link #saveUserToFirestore(String, String)} if successful.
     * If unsuccessful, will notify user signup has failed
     *
     * @param email    The user's email address.
     * @param password The user's chosen password.
     * @param username The user's chosen username.
     */
    private void createUserWithEmailAndUsername(String email, String password, String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToFirestore(username, email);
                    } else {
                        Toast.makeText(getActivity(), "Signup Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Saves the username and email of the newly created user to Firestore.
     *
     * @param username The username of the new user.
     * @param email    The email of the new user.
     */
    private void saveUserToFirestore(String username, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);

        mFirestore.collection("users").document(username).set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Successful Sign Up", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to save user", Toast.LENGTH_SHORT).show();
                });
    }
}
