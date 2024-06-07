package com.dicoding.picodiploma.dicodingstory.view.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.picodiploma.dicodingstory.R
import com.dicoding.picodiploma.dicodingstory.view.EspressoIdlingResource
import com.dicoding.picodiploma.dicodingstory.view.main.MainActivity
import com.dicoding.picodiploma.dicodingstory.view.welcome.WelcomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun login_Success() {
        Intents.init()
        onView(withId(R.id.ed_login_email))
            .perform(
                ViewActions.typeText("testingsajaa@gmail.com"),
                ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.ed_login_password))
            .perform(ViewActions.typeText("testingsajaa"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
        onView(withText(R.string.yeah))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withText(R.string.login_success))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withText(R.string.next))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
        intended(hasComponent(MainActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun login_Fail() {
        onView(withId(R.id.ed_login_email))
            .perform(
                ViewActions.typeText("emailsalah@gmail.com"),
                ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.ed_login_password))
            .perform(ViewActions.typeText("passwordsalah"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
        onView(withText(R.string.oops))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withText(R.string.ok))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
    }

    @Test
    fun logout_Success() {
        Intents.init()
        onView(withId(R.id.ed_login_email))
            .perform(
                ViewActions.typeText("testingsajaa@gmail.com"),
                ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.ed_login_password))
            .perform(ViewActions.typeText("testingsajaa"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())
        onView(withText(R.string.yeah))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withText(R.string.login_success))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
        onView(withText(R.string.next))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
        intended(hasComponent(MainActivity::class.java.name))
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(R.string.logout)).perform(click())
        intended(hasComponent(WelcomeActivity::class.java.name))
        Intents.release()
    }
}

