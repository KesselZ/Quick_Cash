package com.example.project1;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignUpPageTest {

    @Rule
    public ActivityScenarioRule<SignUpPage> signRule = new ActivityScenarioRule<>(SignUpPage.class);
    public IntentsTestRule<SignUpPage> signUpIntent = new IntentsTestRule<>(SignUpPage.class);

    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    @Test
    //check if Text and button is successfully shown
    public void checkIfSignUpPageShow() {
        onView(withId(R.id.userName)).check(matches(withText("")));
        onView(withId(R.id.password)).check(matches(withText("")));
        onView(withId(R.id.submit)).check(matches(withText("create")));
        onView(withId(R.id.BacktoLogin)).check(matches(withText("Back")));
    }


    @Test
    //check if there is an error message when username is empty
    public void isEmptyUserName() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("123456789"));
        onView(withId(R.id.submit)).perform(closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorMessage)).check(matches(withText("Please enter your username.")));
    }

    @Test
    //check if there is an error message when password is empty
    public void isEmptyPassword() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("abcd1234"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.submit)).perform(closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorMessage)).check(matches(withText("Please enter your user password.")));
    }


    @Test
    //check if there is an error message when username is not valid
    public void isValidUserNameUICheck() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("ab"));
        onView(withId(R.id.password)).perform(typeText("123456789"));
        onView(withId(R.id.submit)).perform(closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorMessage)).check(matches(withText("Your username must be a combination of numbers, lower or upper case letters(4-16 characters)")));
    }


    @Test
    //check if there is an error message when password is not valid
    public void isValidPasswordUICheck() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("abcd123"));
        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.submit)).perform(closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorMessage)).check(matches(withText("Your password must be a combination of numbers, lower or upper case letters(8-16 characters)")));
    }


    @Test
    //check if back button works
    public void goToLogin() {
        onView(withId(R.id.BacktoLogin)).perform(click());
        intended(hasComponent(LogIn.class.getName()));
    }


    @AfterClass
    public static void tearDown() {
        System.gc();
    }
}