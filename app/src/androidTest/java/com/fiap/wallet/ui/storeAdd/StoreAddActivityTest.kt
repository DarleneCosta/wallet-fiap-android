package com.fiap.wallet.ui.storeAdd



import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fiap.wallet.R
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class StoreAddActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(StoreAddActivity::class.java)

    @Test
    fun storeAddActivityTest() {

        Thread.sleep(1000)

        onData(anything())
            .inAdapterView(
                (withId(R.id.listView))
            )
            .atPosition(0).perform(click())


        Thread.sleep(700)

        onView(withId(R.id.btnAddStore)).perform(click())

        Thread.sleep(700)

        onView(withId(R.id.btnMenu)).check(ViewAssertions.matches(isDisplayed()))
    }

}
