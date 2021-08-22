package com.example.project1;

import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class changePwdActivityTest {



    @Rule
    public IntentsTestRule<changePwdActivity> mIntentTestRule = new IntentsTestRule<>(changePwdActivity.class);
    public ActivityScenarioRule<changePwdActivity> myRule = new ActivityScenarioRule<>(changePwdActivity.class);



    @Test

    public void checkIfChangePwdPageIsShown() {
        onView(withId(R.id.changePwdBtn)).check(matches(not(isDisplayed())));
        onView(withId(R.id.changePwd_password)).check(matches(isDisplayed()));
        onView(withId(R.id.changePwd_userName)).check(matches((isDisplayed())));
        onView(withId(R.id.sendVerificationBtn)).check(matches((isDisplayed())));
    }


    @Test

    public void checkIfChangeBtnPageIsShown()  {
        onView(withId(R.id.changePwd_userName)).perform(typeText("test456"), closeSoftKeyboard());

        onView(withId(R.id.sendVerificationBtn)).perform(click());
        onView(withId(R.id.changePwdBtn)).check(matches(isDisplayed()));


    }

    



}