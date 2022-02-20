package com.fiap.wallet.viewModel.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.LoginRequest
import com.fiap.wallet.models.LoginResponse
import com.fiap.wallet.repositories.login.LoginRepository
import com.fiap.wallet.viewModel.login.LoginViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<LoginRepository>()
    private val liveDataSignUp = mockk<Observer<LoginResponse>>(relaxed = true)

    @Test
    fun `should sign in with success`() {
        var viewModel = instantiateViewModel()

        val login = LoginRequest("123", "1234")
        val response = LoginResponse("abc-123", "123")

        every { repository.login(login) } returns MockCall<LoginResponse>(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.login(login)

        verify { repository.login(login) }
        verify { liveDataSignUp.onChanged(response) }
    }

    @Test
    fun `should not sign in with success`() {
        var viewModel = instantiateViewModel()

        val login = LoginRequest("123", "1234")
        val response = LoginResponse("abc-123", "123")

        every { repository.login(login) } returns MockCall<LoginResponse>(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.login(login);

        verify { repository.login(login) }
        verify { liveDataSignUp.onChanged(null) }
    }

    private fun instantiateViewModel(): LoginViewModel {
        val viewModel = LoginViewModel(repository);
        viewModel.success.observeForever(liveDataSignUp)
        return viewModel
    }
}