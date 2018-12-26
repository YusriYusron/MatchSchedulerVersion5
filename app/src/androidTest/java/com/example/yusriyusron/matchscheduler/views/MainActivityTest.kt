package com.example.yusriyusron.matchscheduler.views

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.yusriyusron.matchscheduler.R
import com.example.yusriyusron.matchscheduler.R.id.add_to_favorite
import com.example.yusriyusron.matchscheduler.R.id.viewPagerLayout
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(2000)
        onView(withId(viewPagerLayout)).check(matches(isDisplayed()))
        onView(withId(viewPagerLayout)).perform(click())
    }

    @Test
    fun testAppBehavior() {
        Thread.sleep(2000)
        onView(withId(viewPagerLayout)).check(matches(isDisplayed()))
        onView(withId(viewPagerLayout)).perform(click())
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()
        onView(withId(R.id.tLayout)).check(matches(isDisplayed()))
        onView(withText("Next Match")).perform(click())
        onView(withId(viewPagerLayout)).perform(click())
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()
        onView(withText("Favorite")).perform(click())
        Thread.sleep(2000)
    }
}