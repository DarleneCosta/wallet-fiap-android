package com.fiap.wallet.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun loginActivityTest() {

        Thread.sleep(1000)

        onView(withId(R.id.txtCpf)).perform(click())

        onView(withId(R.id.txtCpf)).perform(replaceText("55555555555"), closeSoftKeyboard())

        onView(withId(R.id.txtCpf)).perform(pressImeActionButton())

        onView(withId(R.id.txtSenha)).perform(replaceText("teste555"), closeSoftKeyboard())

        onView(withId(R.id.txtSenha)).perform(pressImeActionButton())

        onView(withId(R.id.btnLogin)).perform(click())

        Thread.sleep(700)

        onView(withId(R.id.btnMenu)).check(ViewAssertions.matches(isDisplayed()))


    }
}
