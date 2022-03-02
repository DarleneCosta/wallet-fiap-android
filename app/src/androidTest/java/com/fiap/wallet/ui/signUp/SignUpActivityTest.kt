package com.fiap.wallet.ui.signUp

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
class SignUpActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SignUpActivity::class.java)

    @Test
    fun signUpActivityTest() {

        Thread.sleep(1000)

        onView(withId(R.id.layout1)).perform(click())

        onView(withId(R.id.txtNome)).perform(replaceText("Thiago Torres"), closeSoftKeyboard())

        onView(withId(R.id.txtEmail)).perform(replaceText("tgTorres@teste.com"), closeSoftKeyboard())

        onView(withId(R.id.txtCpf)).perform(replaceText("55555555555"), closeSoftKeyboard())

        onView(withId(R.id.txtSenha)).perform(replaceText("teste555"), closeSoftKeyboard())

        onView(withId(R.id.txtSenha)).perform(pressImeActionButton())

        onView(withId(R.id.swtNotification)).perform(click())

        onView(withId(R.id.btnSignup)).perform(click())

        Thread.sleep(500)

        onView(withId(R.id.imgLogin)).check(ViewAssertions.matches(isDisplayed()))
    }

}
