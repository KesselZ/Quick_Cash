package com.example.project1;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class EditFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkLoggedMyPostPage() throws InterruptedException {
        //login process
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("qwert5678"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        //
        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText("My Post")).perform(click());
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close());
        onData(anything()).inAdapterView(withId(R.id.tasklistView)).atPosition(0).onChildView(withId(R.id.editBtn)).check(matches(isDisplayed())).perform(click());

        //editfragment
        onView(withId(R.id.editTitle)).check(matches((isDisplayed())));
        onView(withId(R.id.editDescription)).check(matches((isDisplayed())));
        onView(withId(R.id.editWage)).check(matches((isDisplayed())));
        onView(withId(R.id.editSelectDate)).check(matches((isDisplayed())));
        onView(withId(R.id.editDateView)).check(matches((isDisplayed())));
        onView(withId(R.id.editBtn)).check(matches((isDisplayed())));
    }
}