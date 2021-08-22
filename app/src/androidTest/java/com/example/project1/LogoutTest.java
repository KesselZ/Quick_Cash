package com.example.project1;

import android.content.Context;

import androidx.test.espresso.contrib.DrawerActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LogoutTest {


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project1", appContext.getPackageName());
    }


    @Rule
    public IntentsTestRule<LogIn> mIntentTestRule = new IntentsTestRule<>(LogIn.class);
    public ActivityScenarioRule<LogIn> myRule = new ActivityScenarioRule<>(LogIn.class);


    /**
     *check if the menu item's title successfully change to "Logout"
     */
    @Test
    public void checkIfLogoutBtnShown() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("4444"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("fefsfsef"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(2000);
        //open drawer
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(withText(R.string.logoutBtn)).check(matches(withText("Logout")));

    }

    /*** AT2:2**/
    @Test
    public void checkIfLogoutBtnWorks() throws InterruptedException {
        onView(withId(R.id.userName)).perform(typeText("4444"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("fefsfsef"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        Thread.sleep(2000);

        //open drawer
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        //onView(withId(R.id.nav_view))
        //.perform(NavigationViewActions.navigateTo(R.id.nav_setting));

        onView(withText(R.string.logoutBtn)).perform(click());
        intended(hasComponent(LogIn.class.getName()));

    }


}
