package com.fiap.wallet.ui.store

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
class StoreActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(StoreActivity::class.java)

    @Test
    fun storeActivityTest() {

        Thread.sleep(1000)

        onView(withId(R.id.btn_delete)).perform(click())

        onView(withId(android.R.id.button1)).perform(scrollTo(), click())

        Thread.sleep(700)

    }
}
