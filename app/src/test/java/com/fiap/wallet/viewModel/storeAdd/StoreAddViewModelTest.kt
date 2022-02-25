package com.fiap.wallet.viewModel.storeAdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.Store
import com.fiap.wallet.repositories.storeAdd.StoreAddRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test

class StoreAddViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<StoreAddRepository>()
    private val listObserver = mockk<Observer<List<Store>>>(relaxed = true)
    private val error = mockk<Observer<String>>(relaxed = true)
    private val status = mockk<Observer<Boolean>>(relaxed = true)

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

    @Test
    fun `deveria retornar a lista de lojas pesquisa pelo nome`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"

        val searchLoja = "Americanas"

        val response = arrayListOf(
            Store(1, "Americanas", "1234", 5L, "asdas.com.br")
        )

        every { repository.getStoreSearch(searchLoja, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.getStoreSearch(searchLoja, fakeToken)

        verify { repository.getStoreSearch(searchLoja, fakeToken) }
        verify { listObserver.onChanged(response) }
    }

    @Test
    fun `deveria exibir erro quando a request de buscar pesquisa pelo nome da loja falhar`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"

        val response = arrayListOf(
            Store(1, "Americanas", "1234", 5L, "asdas.com.br")
        )

        val searchLoja = "Americanas"
        every { repository.getStoreSearch(searchLoja, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.getStoreSearch(searchLoja, fakeToken)


        verify { repository.getStoreSearch(searchLoja, fakeToken) }
        verify(exactly = 0) { listObserver.onChanged(any()) }
        verify { error.onChanged("Mock failed successfully") }
    }

    @Test
    fun `deveria confirmar adicao loja a lista de lojas favoritas`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.addStorePreference(fakeCpf, idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.addStorePreference(fakeCpf, idStore, fakeToken)

        verify { repository.addStorePreference(fakeCpf, idStore, fakeToken) }
        verify { status.onChanged(true) }
    }

    @Test
    fun `deveria exibir erro quando a requisicao para salvar alteracao falha`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.addStorePreference(fakeCpf, idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.addStorePreference(fakeCpf, idStore, fakeToken)

        verify { repository.addStorePreference(fakeCpf, idStore, fakeToken) }
        verify { status.onChanged(false) }
    }

    private fun instantiateViewModel(): StoreAddViewModel {
        val viewModel = StoreAddViewModel(repository)
        viewModel.status.observeForever(status)
        viewModel.store.observeForever(listObserver)
        viewModel.storeList.observeForever(listObserver)
        viewModel.errorMessage.observeForever(error)
        return viewModel
    }
}