package com.example.a301groupproject.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a301groupproject.MainActivity;
import com.example.a301groupproject.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.buttonSignIn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement your login logic here

                boolean isLoginSuccessful = true; // This is just a placeholder. You should replace this with your actual login logic.

                if (isLoginSuccessful) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // This will finish the current hosting Activity
                }
            }
        });

        TextView signUpTextView = view.findViewById(R.id.textViewSignUp);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment()) // assuming 'fragment_container' is the ID of your FrameLayout in LoginActivity's layout
                        .addToBackStack(null)  // this allows you to navigate back to the LoginFragment by pressing the back button
                        .commit();
            }
        });

        Button SignInButton = view.findViewById(R.id.buttonSignIn);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish(); // Optional: if you want to remove the LoginActivity from the back stack
                }

            }
        });



        return view;
    }

}
