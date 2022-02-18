package fiap.com.wallet.login.dto.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import fiap.com.wallet.MockCall
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.dto.LoginResponseDTO
import fiap.com.wallet.login.repository.LoginRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<LoginRepository>()
    private val liveDataSignUp = mockk<Observer<LoginResponseDTO>>(relaxed = true)

    @Test
    fun `should sign in with success`() {
        var viewModel = instantiateViewModel()

        val login = LoginDTO("123", "1234")
        val response = LoginResponseDTO("abc-123", "123")

        every { repository.login(login) } returns MockCall<LoginResponseDTO>(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.login(login);

        verify { repository.login(login) }
        verify { liveDataSignUp.onChanged(response)}
    }

    @Test
    fun `should not sign in with success`() {
        var viewModel = instantiateViewModel()

        val login = LoginDTO("123", "1234")
        val response = LoginResponseDTO("abc-123", "123")

        every { repository.login(login) } returns MockCall<LoginResponseDTO>(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.login(login);

        verify { repository.login(login) }
        verify { liveDataSignUp.onChanged(null)}
    }

    private fun instantiateViewModel(): LoginViewModel {
        val viewModel = LoginViewModel(repository);
        viewModel.liveDataSignUp.observeForever(liveDataSignUp)
        return viewModel;
    }
}