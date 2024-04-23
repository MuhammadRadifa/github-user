package com.dicoding.githubuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githubuser.ui.MainActivity
import com.dicoding.githubuser.utils.UserAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class FavoriteTest {
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
    fun addFavoriteUser(){
        Thread.sleep(3000)
        onView(withId(R.id.userList)).check(matches(isDisplayed()))

        onView(withId(R.id.userList)).perform(
            actionOnItemAtPosition<UserAdapter.MyViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(2000)
        onView(withId(R.id.favorite_button)).perform(click())
        onView(withId(R.id.favorite_page)).perform(click())

        onView(withId(R.id.userList)).check(matches(isDisplayed()))
    }

    @Test
    fun deleteFavorite(){
        onView(withId(R.id.favorite_page)).perform(click())
        onView(withId(R.id.userList)).check(matches(isDisplayed()))

        onView(withId(R.id.userList)).perform(
            actionOnItemAtPosition<UserAdapter.MyViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(2000)
        onView(withId(R.id.favorite_button)).perform(click())
        onView(withId(R.id.favorite_page)).perform(click())

        onView(withId(R.id.emptyUser)).check(matches(isDisplayed()))
    }

}