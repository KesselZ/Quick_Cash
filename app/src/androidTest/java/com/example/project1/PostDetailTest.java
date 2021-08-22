package com.example.project1;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;


/**
 * Assume LocationPermission is given on the phone
 */
public class PostDetailTest {

    @Rule
    public IntentsTestRule<MainActivity> mIntentTestRule = new IntentsTestRule<>(MainActivity.class);
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    /*** AT9:1**/
    //check if successfully move to PostDetail activity
    public void checkIfMoveToPostDetailActivity() throws InterruptedException {
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.HomeListView)).atPosition(0).check(matches(isDisplayed())).perform(click());
        intended(hasComponent(PostDetail.class.getName()));
    }


    /*** AT9:1**/
    @Test
    //check MapFragment  is successfully shown
    public void checkMapFragmentSuccessfullyShown() throws InterruptedException {
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.HomeListView)).atPosition(0).check(matches(isDisplayed())).perform(click());
        intended(hasComponent(PostDetail.class.getName()));
        onView(withId(R.id.map)).check(matches((isDisplayed())));

    }


    @Test
    /*** AT9:2**/
    //check MapFragment  is successfully shown
    public void checkGoogleMapSuccessfullyLoaded() throws InterruptedException {
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.HomeListView)).atPosition(0).check(matches(isDisplayed())).perform(click());
        intended(hasComponent(PostDetail.class.getName()));
        Thread.sleep(500);
        onView(withContentDescription("Google Map")).perform(swipeUp());
        Thread.sleep(500);
        onView(withContentDescription("Google Map")).perform(swipeDown());
        Thread.sleep(500);
        onView(withContentDescription("Google Map")).perform(swipeLeft());
        Thread.sleep(500);
        onView(withContentDescription("Google Map")).perform(swipeRight());
    }

}
