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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Test
    public void useAppContext() {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project1", appContext.getPackageName());
    }


    @Rule
    public IntentsTestRule<LogIn> mIntentTestRule = new IntentsTestRule<>(LogIn.class);
    public ActivityScenarioRule<LogIn> myRule = new ActivityScenarioRule<>(LogIn.class);


    /*** AT1:1**/
    @Test
    //check if Text and button is successfully shown
    public void checkIfLoginPageIsShown() {
        onView(withId(R.id.userName)).check(matches(withText("")));
        onView(withId(R.id.password)).check(matches(withText("")));
        onView(withId(R.id.toSignUp)).check(matches(withText("Sign up")));
        onView(withId(R.id.loginBtn)).check(matches(withText("Log In")));
    }


    /*** AT1:2**/
    @Test
    //check if there is an error message when username is empty
    public void EmptyLogin() {

        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.status)).check(matches(withText("Enter your username")));

    }
    /*** AT1:2**/
    @Test
    //check if there is an error message when password is empty
    public void EmptyPassword() {

        onView(withId(R.id.userName)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.status)).check(matches(withText("Enter your password")));

    }
    /*** AT1:2**/
    @Test
    //check if it will login when username does not exist
    public void UsernameNotExist() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("texxsa"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.status)).check(matches(withText("Incorrect username or password")));

    }

    /*** AT1:2**/
    @Test
    //check if it will login when password is not correct
    public void WrongPassword() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("4444"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("ff"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.status)).check(matches(withText("Incorrect username or password")));

    }

    /*** AT1:1**/
    @Test
    //if it will jump to MainActivity after login successfully
    public void LoginSuccess() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(1000);
        intended(hasComponent(MainActivity.class.getName()));

    }

    /*** AT1:1**/
    @Test
    //if it will send information to MainActivity
    public void LoginWithUserInformation() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(1000);
        intended(hasExtra("UserName", "test456"));

    }

    
    @Test
    //if it will jump to SignUpPage after click the register button
    public void GotoRegister() {

        onView(withId(R.id.toSignUp)).perform(click());

        intended(hasComponent(SignUpPage.class.getName()));

    }


}