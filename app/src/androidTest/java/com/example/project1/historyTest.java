package com.example.project1;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class historyTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);


    /*** AT11:1**/
    @Test
    public void checkUnloggedhistoryPage() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("History")).perform(click());
        onView(withId(R.id.tasklistView)).check(doesNotExist());
        onView(withId(R.id.loginPrompt)).check(matches(withText("Please Login First")));
    }
    /*** AT11:2**/
    @Test
    public void checkLoggedhistoryPage() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("History")).perform(click());
        onView(withId(R.id.HistoryListView)).check(matches(isDisplayed()));
    }

}
