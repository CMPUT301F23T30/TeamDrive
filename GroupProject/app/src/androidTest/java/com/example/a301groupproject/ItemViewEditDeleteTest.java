package com.example.a301groupproject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ItemViewEditDeleteTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Test
    public void EditItemFragmentTest(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.itemNameInput)).perform(ViewActions.typeText("Engine"));
        onView(withId(R.id.itemNameInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.itemNameInput)).check(matches(withText("Engine")));

        onView(withId(R.id.itemModelInput)).perform(ViewActions.typeText("1"));
        onView(withId(R.id.itemModelInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.itemModelInput)).check(matches(withText("1")));

        onView(withId(R.id.itemMakeInput)).perform(ViewActions.typeText("Ger"));
        onView(withId(R.id.itemMakeInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.itemMakeInput)).check(matches(withText("Ger")));

        onView(withId(R.id.input_year)).perform(ViewActions.typeText("2000"));
        onView(withId(R.id.input_year)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.input_year)).check(matches(withText("2000")));

        onView(withId(R.id.input_month)).perform(ViewActions.typeText("10"));
        onView(withId(R.id.input_month)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.input_month)).check(matches(withText("10")));

        onView(withId(R.id.input_day)).perform(ViewActions.typeText("12"));
        onView(withId(R.id.input_day)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.input_day)).check(matches(withText("12")));

        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.typeText("42"));
        onView(withId(R.id.estimatedValueInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.estimatedValueInput)).check(matches(withText("42")));

        onView(withId(R.id.serialNumberInput)).perform(ViewActions.typeText("100"));
        onView(withId(R.id.serialNumberInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.serialNumberInput)).check(matches(withText("100")));

        onView(withId(R.id.descriptionInput)).perform(ViewActions.typeText("none"));
        onView(withId(R.id.descriptionInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.descriptionInput)).check(matches(withText("none")));

        onView(withId(R.id.commentInput)).perform(ViewActions.typeText("ok"));
        onView(withId(R.id.commentInput)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.commentInput)).check(matches(withText("ok")));

        onView(withId(R.id.confirmButton)).perform(click());

    }



}
