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

public class chatFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    /*** AT10:2**/
    @Test
    public void checkUnloggedMyPostPage() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Chat")).perform(click());
        onView(withId(R.id.userListView)).check(doesNotExist());
        onView(withId(R.id.loginPrompt)).check(matches(withText("Please Login First")));
    }
    /*** AT10:3**/
    @Test
    public void checkLoggedChatPage() throws InterruptedException {
        // login process
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

        Thread.sleep(2000);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Chat")).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(R.id.userListView)).check(matches(isDisplayed()));
    }

}
