package com.fiap.wallet.viewModel.signUp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.LoginRequest
import com.fiap.wallet.models.LoginResponse
import com.fiap.wallet.models.SignUp
import com.fiap.wallet.repositories.login.LoginRepository
import com.fiap.wallet.repositories.signUp.SignupRepository
import com.fiap.wallet.viewModel.login.LoginViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test

class SignupViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<SignupRepository>()
    private val status = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `should sign up with success`() {
        val viewModel = instantiateViewModel()

        val cadastro = SignUp("joão vitor","jvitor.almeida98@gmail.com","123456789","minhasenha",true)
        val response = ResponseBody.create("application/json".toMediaTypeOrNull(),"")

        every { repository.signUp(cadastro) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.signUp(cadastro)

        verify { repository.signUp(cadastro) }
        verify { status.onChanged(true) }
    }

    @Test
    fun `should not sign in with success`() {
      val viewModel = instantiateViewModel()

        val cadastro = SignUp("joão vitor","jvitor.almeida98@gmail.com","123456789","minhasenha",true)
        val response = ResponseBody.create("application/json".toMediaTypeOrNull(),"")

        every { repository.signUp(cadastro) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.signUp(cadastro)

        verify { repository.signUp(cadastro) }
        verify { status.onChanged(false) }
    }

    private fun instantiateViewModel(): SignupViewModel {
        val viewModel = SignupViewModel(repository)
        viewModel.status.observeForever(status)
        return viewModel
    }
}