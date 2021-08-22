package com.example.project1.ui.mypost;

import androidx.annotation.ContentView;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.project1.MainActivity;
import com.example.project1.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MyPostFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void checkUnloggedMyPostPage() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("My Post")).perform(click());
        onView(withId(R.id.tasklistView)).check(doesNotExist());
        onView(withId(R.id.loginPrompt)).check(matches(withText("Please Login First")));
    }

    @Test
    public void checkLoggedMyPostPage() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("My Post")).perform(click());
        onView(withId(R.id.tasklistView)).check(matches(isDisplayed()));
    }
}