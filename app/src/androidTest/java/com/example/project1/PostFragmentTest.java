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

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PostFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void checkNotLoggedPostPage()  {
        //open drawer
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText(R.string.post_tasks)).perform(click());
        onView(withId(R.id.taskTitle)).check(doesNotExist());
        onView(withId(R.id.taskDescription)).check(doesNotExist());
        onView(withId(R.id.taskWage)).check(doesNotExist());
        onView(withId(R.id.selectDate)).check(doesNotExist());
        onView(withId(R.id.dateView)).check(doesNotExist());
        onView(withId(R.id.postBtn)).check(doesNotExist());
        onView(withId(R.id.loginPrompt)).check(matches(withText("Please Login First")));

    }
    @Test
    public void checkLoggedPostPage() throws InterruptedException {
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
        onView(withText(R.string.post_tasks)).perform(click());
        onView(withId(R.id.taskTitle)).check(matches((isDisplayed())));
        onView(withId(R.id.taskDescription)).check(matches((isDisplayed())));
        onView(withId(R.id.taskWage)).check(matches((isDisplayed())));
        onView(withId(R.id.selectDate)).check(matches((isDisplayed())));
        onView(withId(R.id.dateView)).check(matches((isDisplayed())));
        onView(withId(R.id.postBtn)).check(matches((isDisplayed())));

    }
    @Test
    public void checkPostBtn() throws InterruptedException {
       //login process
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("qwert5678"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.loginBtn)).perform(click());

        //open post page
        Thread.sleep(2000);

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText(R.string.post_tasks)).perform(click());
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close());
        //input information

        onView(withId(R.id.taskTitle)).perform(typeText("exampleTitle"), closeSoftKeyboard());
        onView(withId(R.id.taskDescription)).perform(typeText("Example Description\n\nExample\n\nExample"), closeSoftKeyboard());
        onView(withId(R.id.taskWage)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.selectDate)).perform(click());

        onView(withText("OK")).perform(click());
        onView(withId(R.id.postBtn)).perform(click());

        //check status
        onView(withId(R.id.postStatus)).check(matches(withText("Post Successfully")));
    }


    @Test
    public void checkEmptyPost() throws InterruptedException {
        //login process
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText("Login")).perform(click());
        onView(withId(R.id.userName)).perform(typeText("test456"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("qwert5678"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.loginBtn)).perform(click());

        //open post page
        Thread.sleep(2000);

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        onView(withText(R.string.post_tasks)).perform(click());
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close());
        //input information

        onView(withId(R.id.taskTitle)).perform(typeText("exampleTitle"), closeSoftKeyboard());

        onView(withId(R.id.postBtn)).perform(click());

        //check status
        onView(withId(R.id.postStatus)).check(matches(withText("Please Fill all the Blanks")));
    }
}
