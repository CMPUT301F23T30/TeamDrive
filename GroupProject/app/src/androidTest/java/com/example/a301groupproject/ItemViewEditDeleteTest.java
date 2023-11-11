package com.example.a301groupproject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ItemViewEditDeleteTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> scenario = new ActivityScenarioRule(LoginActivity.class);
    private void login() {
        onView(withId(R.id.loginEmail)).perform(ViewActions.typeText("E2@gmail.com"));
        onView(withId(R.id.loginEmail)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(ViewActions.typeText("123456"));
        onView(withId(R.id.loginPassword)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonSignIn)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void objSetup(){
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.itemNameInput)).perform(ViewActions.typeText("Engine"));
        onView(withId(R.id.itemNameInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.itemModelInput)).perform(ViewActions.typeText("1"));
        onView(withId(R.id.itemModelInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.itemMakeInput)).perform(ViewActions.typeText("Ger"));
        onView(withId(R.id.itemMakeInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_year)).perform(ViewActions.typeText("2000"));
        onView(withId(R.id.input_year)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_month)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.input_month)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.input_day)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.input_day)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.typeText("42"));
        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.serialNumberInput)).perform(ViewActions.typeText("100"));
        onView(withId(R.id.serialNumberInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.descriptionInput)).perform(ViewActions.typeText("none"));
        onView(withId(R.id.descriptionInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.commentInput)).perform(ViewActions.typeText("ok"));
        onView(withId(R.id.commentInput)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.itemTagInput)).perform(ViewActions.typeText("love"));
        onView(withId(R.id.addtagbutton)).perform(click());

        onView(withId(R.id.confirmButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void viewAndEditTest(){
        login();
        objSetup();
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.itemNameInput)).check(matches(withText("Engine")));
        onView(withId(R.id.itemModelInput)).check(matches(withText("1")));
        onView(withId(R.id.itemMakeInput)).check(matches(withText("Ger")));
        onView(withId(R.id.input_year)).check(matches(withText("2000")));
        onView(withId(R.id.input_month)).check(matches(withText("10")));
        onView(withId(R.id.input_day)).check(matches(withText("12")));
        onView(withId(R.id.estimatedValueInput)).check(matches(withText("42")));
        onView(withId(R.id.serialNumberInput)).check(matches(withText("100")));
        onView(withId(R.id.descriptionInput)).check(matches(withText("none")));
        onView(withId(R.id.commentInput)).check(matches(withText("ok")));

        onView(withId(R.id.itemNameInput)).perform(ViewActions.typeText("-new"));
        onView(withId(R.id.itemNameInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editScroll))
                .perform(ViewActions.swipeUp());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //onView(withId(R.id.chipgroup))
        onView(withId(R.id.confirmButton)).perform(click());

        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.itemNameInput)).check(matches(withText("Engine-new")));



    }
    @Test
    public void viewDeleteTest(){
        login();
        onView(withId(R.id.recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editScroll))
                .perform(ViewActions.swipeUp());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.deleteButton)).perform(click());


    }




}
