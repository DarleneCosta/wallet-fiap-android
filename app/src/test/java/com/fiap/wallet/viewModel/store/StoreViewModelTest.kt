package com.fiap.wallet.viewModel.store

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.Store
import com.fiap.wallet.repositories.store.StoreRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test

class StoreViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<StoreRepository>()
    private val listObserver = mockk<Observer<List<Store>>>(relaxed = true)
    private val error = mockk<Observer<String>>(relaxed = true)
    private val status = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `deveria retornar a lista das lojas favoritas mockada`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStorePreference(cpfFake,fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.getAllStore(cpfFake,fakeToken)

        verify { repository.getAllStorePreference(cpfFake,fakeToken) }
        verify { listObserver.onChanged(response) }
    }

    @Test
    fun `deveria exibir erro quando a request que lista das lojas favoritas falhar`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStorePreference(cpfFake,fakeToken)  } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.getAllStore(cpfFake,fakeToken)


        verify { repository.getAllStorePreference(cpfFake,fakeToken) }
        verify(exactly = 0) { listObserver.onChanged(any()) }
        verify { error.onChanged("Mock failed successfully") }
    }

    @Test
    fun `deveria confirmar remocao a loja a lista de lojas favoritas`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.removeStorePreference(fakeCpf,idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.removeStorePreference(fakeCpf,idStore, fakeToken)

        verify { repository.removeStorePreference(fakeCpf,idStore, fakeToken)}
        verify { status.onChanged( true) }
    }

    @Test
    fun `deveria exibir erro quando a requisicao para salvar alteracao falha`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.removeStorePreference(fakeCpf,idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.removeStorePreference(fakeCpf,idStore, fakeToken)

        verify { repository.removeStorePreference(fakeCpf,idStore, fakeToken) }
        verify { status.onChanged(false) }
    }

    private fun instantiateViewModel(): StoreViewModel {
        val viewModel = StoreViewModel(repository)
        viewModel.status.observeForever(status)
        viewModel.storeList.observeForever(listObserver)
        viewModel.errorMessage.observeForever(error)
        return viewModel
    }
}