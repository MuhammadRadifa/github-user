package com.dicoding.githubuser

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githubuser.ui.MainActivity
import com.dicoding.githubuser.utils.UserAdapter
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class UserTest {

    private val username = "muhammadradifa"
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
    fun searchUser(){
        onView(withId(R.id.searchBar)).check(matches(isDisplayed())).perform(click())
        onView(isAssignableFrom(EditText::class.java))

        Thread.sleep(3000)

        onView(isAssignableFrom(EditText::class.java))
            .perform(
                clearText(),
                typeText(username),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )

        Thread.sleep(3000)
        onView(withId(R.id.userList)).check(matches(isDisplayed()))

        onView(withId(R.id.userList)).perform(
            actionOnItemAtPosition<UserAdapter.MyViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(2000)
        onView(allOf(withId(R.id.textViewUsername),
            ViewMatchers.withParent(withId(R.id.userDetail))
        )).check(matches(isDisplayed()))
    }

    @Test
    fun showUserDetail(){
        Thread.sleep(3000)

        onView(withId(R.id.userList)).check(matches(isDisplayed()))

        onView(withId(R.id.userList)).perform(
            actionOnItemAtPosition<UserAdapter.MyViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(2000)
        onView(withId(R.id.textViewUsername)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescription)).check(matches(isDisplayed()))
    }
}