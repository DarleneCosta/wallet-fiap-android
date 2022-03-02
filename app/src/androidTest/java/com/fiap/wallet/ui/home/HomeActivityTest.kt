package com.fiap.wallet.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fiap.wallet.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {

        Thread.sleep(700)

        onView(withId(R.id.btnLogin)).perform(click())
        onView(withId(R.id.imgLogin)).check(ViewAssertions.matches(isDisplayed()))
        Thread.sleep(700)

        pressBack()

        Thread.sleep(700)

        onView(withId(R.id.btnNewUser)).perform(click())
        onView(withId(R.id.imgSignup)).check(ViewAssertions.matches(isDisplayed()))

        pressBack()
    }

}
