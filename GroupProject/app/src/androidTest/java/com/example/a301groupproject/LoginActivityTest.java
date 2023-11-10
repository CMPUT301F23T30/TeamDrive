package com.example.a301groupproject;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Root;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.hamcrest.TypeSafeMatcher;

import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.Toast;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

import java.util.UUID;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    private static final String INVALID_EMAIL_TO_BE_TYPED = "wrong@example.com";
    private static final String INVALID_PASSWORD_TO_BE_TYPED = "wrongpassword";
    private static final String VALID_EMAIL_TO_BE_TYPED = "E@gmail.com";
    private static final String VALID_PASSWORD_TO_BE_TYPED = "123456";

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginWithValidCredentials() {
        onView(withId(R.id.loginEmail)).perform(typeText(VALID_EMAIL_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(typeText(VALID_PASSWORD_TO_BE_TYPED), closeSoftKeyboard());

        onView(withId(R.id.buttonSignIn)).perform(click());
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if we are navigated to the MainActivity (assuming MainActivity is the home screen after login)
        onView(withId(R.id.app_bar_main)).check(matches(isDisplayed()));
    }
    @Test
    public void redirectToSignUpPage() {
        // Click the sign-up TextView or Button in the LoginFragment
        onView(withId(R.id.textViewSignUp)).perform(click());
        onView(withId(R.id.editTextUserName)).check(matches(isDisplayed()));
    }

    private void navigateToSignUpFragment() {
        onView(withId(R.id.textViewSignUp)).perform(click());
    }

    @Test
    public void signUpWithExistingUsername() {
        navigateToSignUpFragment();
        // Assume '' is an existing username in the database for this test
        onView(withId(R.id.editTextUserName)).perform(typeText("stat"), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.editTextReEnterPassword)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.buttonSignUp)).perform(click());
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if successful signup, will go back to login page, otherwise, stay in this page
        onView(withId(R.id.editTextUserName)).check(matches(isDisplayed()));

    }
    @Test
    public void signUpSuccessfully() {
        // Navigate to the SignUpFragment
        ActivityScenario.launch(LoginActivity.class);
        navigateToSignUpFragment();
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String uniqueUsername = "user_" + UUID.randomUUID().toString().substring(0, 5);
        // Fill in the registration form with valid and unique data
        onView(withId(R.id.editTextUserName)).perform(typeText(uniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).perform(typeText("newuser@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("ValidPassword1"), closeSoftKeyboard());
        onView(withId(R.id.editTextReEnterPassword)).perform(typeText("ValidPassword1"), closeSoftKeyboard());

        // Click the sign-up button
        onView(withId(R.id.buttonSignUp)).perform(click());

        onView(withId(R.id.buttonSignIn)).check(matches(isDisplayed()));
    }


}
