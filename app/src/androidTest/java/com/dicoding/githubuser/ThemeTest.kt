package com.dicoding.githubuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githubuser.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class ThemeTest {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

   @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun setThemeSetting() {
        onView(withId(R.id.dark_mode)).perform(click())
        onView(withId(R.id.dark_mode)).check(matches(
            isDisplayed()
        ))
        onView(withId(R.id.dark_mode)).check(matches(withContentDescription("dark mode")))

        activityScenario.close()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.dark_mode)).check(matches(withContentDescription("dark mode")))
    }

}