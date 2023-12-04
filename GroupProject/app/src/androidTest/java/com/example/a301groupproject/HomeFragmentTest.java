package com.example.a301groupproject;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import android.view.View;

import com.example.a301groupproject.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    private void login() {
        onView(withId(R.id.loginEmail)).perform(ViewActions.typeText("weijia9@ualberta.com"));
        onView(withId(R.id.loginEmail)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(ViewActions.typeText("123456789"));
        onView(withId(R.id.loginPassword)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonSignIn)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Before
    public void setup() {
        // Add first item
        addItem("Engine", "1", "Ger", "2000", "10", "12", "42", "100", "none", "ok");

        // Add second item
        addItem("Transmission", "2", "Ger", "2001", "11", "13", "43", "101", "none", "ok");

        // Add third item
        addItem("Brake", "3", "Ger", "2002", "12", "14", "44", "102", "none", "ok");
    }

    private void addItem(String name, String model, String make, String year, String month, String day, String value, String serial, String description, String comment) {
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.itemNameInput)).perform(ViewActions.typeText(name));
        onView(withId(R.id.itemNameInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.itemModelInput)).perform(ViewActions.typeText(model));
        onView(withId(R.id.itemModelInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.itemMakeInput)).perform(ViewActions.typeText(make));
        onView(withId(R.id.itemMakeInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_year)).perform(ViewActions.typeText(year));
        onView(withId(R.id.input_year)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_month)).perform(ViewActions.typeText(month));
        onView(withId(R.id.input_month)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_day)).perform(ViewActions.typeText(day));
        onView(withId(R.id.input_day)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.typeText(value));
        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.serialNumberInput)).perform(ViewActions.typeText(serial));
        onView(withId(R.id.serialNumberInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.descriptionInput)).perform(ViewActions.typeText(description));
        onView(withId(R.id.descriptionInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.commentInput)).perform(ViewActions.typeText(comment));
        onView(withId(R.id.commentInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.confirmButton)).perform(scrollTo(), click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recyclerViewDisplaysItems() {
        // Check that the RecyclerView is displayed
        login();
        setup();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check that the RecyclerView is displayed
        onView(withId(R.id.recycle_view)).check(matches(isDisplayed()));

        // Check that the first item in the RecyclerView is displayed
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(hasDescendant(withText("Engine"))));

        // Check that the second item in the RecyclerView is displayed
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(1))
                .check(matches(hasDescendant(withText("Transmission"))));

        // Check that the third item in the RecyclerView is displayed
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(2))
                .check(matches(hasDescendant(withText("Brake"))));
    }


    @Test
    public void deleteSelectedItemsUpdatesUI() {
        login();
        setup();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ensure the RecyclerView is displayed
        onView(withId(R.id.recycle_view)).check(matches(isDisplayed()));

        // Scroll to the first item and click the CheckBox
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on a child view with specified id.";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View v = view.findViewById(R.id.checkbox);
                        v.performClick();
                    }
                }));

        // Scroll to the second item and click the CheckBox
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.scrollToPosition(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on a child view with specified id.";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        View v = view.findViewById(R.id.checkbox);
                        v.performClick();
                    }
                }));

        // Click the delete button
        onView(withId(R.id.deleteButton)).perform(click());

        // Check that the first item is no longer in the RecyclerView
        onView(withId(R.id.recycle_view))
                .check(matches(not(withChild(withText("First item text")))));
    }

    @Test
    public void updateTotalValueUpdatesUI() {
        login();
        setup();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.totalValueTextView)).check(matches(withText("Total Value: $129.00")));
    }
}