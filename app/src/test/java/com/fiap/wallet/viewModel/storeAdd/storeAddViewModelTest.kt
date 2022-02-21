package com.fiap.wallet.viewModel.storeAdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.SignUp
import com.fiap.wallet.models.Store
import com.fiap.wallet.repositories.signUp.SignupRepository
import com.fiap.wallet.repositories.storeAdd.StoreAddRepository
import com.fiap.wallet.viewModel.signUp.SignupViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test

class storeAddViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<StoreAddRepository>()
    private val listObserver = mockk<Observer<List<Store>>>(relaxed = true)
    private val error = mockk<Observer<String>>(relaxed = true)

    @Test
    fun `deveria retornar a lista de lojas mockada`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"

        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStore(fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.getAllStore(fakeToken)

        verify { repository.getAllStore(fakeToken) }
        verify { listObserver.onChanged(response) }
    }


    @Test
    fun `deveria exibir erro quando a request de buscar todas as lojas falhar`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"

        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStore(fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.getAllStore(fakeToken)


        verify { repository.getAllStore(fakeToken) }
        verify(exactly = 0) { listObserver.onChanged(any()) }
        verify { error.onChanged("Mock failed successfully") }
    }

    private fun instantiateViewModel(): StoreAddViewModel {
        val viewModel = StoreAddViewModel(repository)
        viewModel.storeList.observeForever(listObserver)
        viewModel.errorMessage.observeForever(error)
        return viewModel
    }
}