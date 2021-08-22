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
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class acceptTaskTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    /*** AT8:1**/
    @Test
    public void checkLoggedMyTaskPage() throws InterruptedException {
        // login
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("abcd"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("abcd1234"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("My Tasks")).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(R.id.tasklistView)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    /*** AT8:2**/
    @Test
    public void checkAcceptedTask() throws InterruptedException {
        // login
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        Thread.sleep(2000);
        onView(withId(R.id.mainSearchView)).perform(typeText("cut the grass\n"));
        Thread.sleep(2000);
        onView(withId(R.id.HomeListView)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }
    /*** AT8:2**/
    @Test
    public void checkAcceptAvailableTask() throws InterruptedException {
        //login process
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        Thread.sleep(2000);
        onView(withId(R.id.mainSearchView)).perform(typeText("\n"));
//        Thread.sleep(2000);
//        onData(anything()).inAdapterView(withId(R.id.HomeListView)).atPosition(0).onChildView(withId(R.id.editBtn)).check(matches(isDisplayed())).perform(click());
//        Thread.sleep(2000);
//        onData(anything()).inAdapterView(withId(R.id.HomeListView)).atPosition(0).onChildView(withId(R.id.editBtn)).check(matches(isDisplayed())).perform(click());

        //
        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("My Tasks")).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(R.id.tasklistView)).check(matches(isDisplayed()));
    }

}
