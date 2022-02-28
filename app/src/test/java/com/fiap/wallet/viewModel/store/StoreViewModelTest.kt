package com.fiap.wallet.viewModel.store

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fiap.wallet.MockCall
import com.fiap.wallet.models.Store
import com.fiap.wallet.models.User
import com.fiap.wallet.models.Wallet
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
    private val wallet = mockk<Observer<Wallet>>(relaxed = true)
    private val error = mockk<Observer<String>>(relaxed = true)
    private val status = mockk<Observer<Boolean>>(relaxed = true)

    @Test
    fun `should return the information of the mocked wallet`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response =  Wallet(6,User( 5, "Roberta Pinheiro","2022-02-28","email@emal.com", "00000000000", "11989966055"),25.9)

        every { repository.getWallet(cpfFake, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.getWallet(cpfFake, fakeToken)

        verify { repository.getWallet(cpfFake, fakeToken) }
        verify { wallet.onChanged(response) }
    }

    @Test
    fun `should show error when request to fetch favorite wallet info fails`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response =  Wallet(6,User( 5, "Roberta Pinheiro","2022-02-28","email@emal.com", "00000000000", "11989966055"),25.9)

        every { repository.getWallet(cpfFake, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.getWallet(cpfFake, fakeToken)

        verify { repository.getWallet(cpfFake, fakeToken) }
        verify(exactly = 0) { wallet.onChanged(any()) }
        verify { error.onChanged("Mock failed successfully") }
    }

    @Test
    fun `should return mocked favorite stores list`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStorePreference(cpfFake, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.getAllStore(cpfFake, fakeToken)

        verify { repository.getAllStorePreference(cpfFake, fakeToken) }
        verify { listObserver.onChanged(response) }
    }

    @Test
    fun `should show error when request which list of favorite stores fails`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val cpfFake = "11111111111"
        val response = arrayListOf(
            Store(1, "americanas", "1234", 5L, "asdas.com.br"),
            Store(2, "C&A", "4321", 10L, "asdas.com.br")
        )

        every { repository.getAllStorePreference(cpfFake, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.getAllStore(cpfFake, fakeToken)


        verify { repository.getAllStorePreference(cpfFake, fakeToken) }
        verify(exactly = 0) { listObserver.onChanged(any()) }
        verify { error.onChanged("Mock failed successfully") }
    }

    @Test
    fun `should confirm removal the store the list of favorite stores`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.removeStorePreference(fakeCpf, idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.success,
            response
        )

        viewModel.removeStorePreference(fakeCpf, idStore, fakeToken)

        verify { repository.removeStorePreference(fakeCpf, idStore, fakeToken) }
        verify { status.onChanged(true) }
    }

    @Test
    fun `should show error when request to save change fails`() {
        val viewModel = instantiateViewModel()

        val fakeToken = "abc-123"
        val fakeCpf = "11111111111"
        val idStore = 1

        val response = "".toResponseBody("application/json".toMediaTypeOrNull())

        every { repository.removeStorePreference(fakeCpf, idStore, fakeToken) } returns MockCall(
            MockCall.ResponseCase.failure,
            response
        )

        viewModel.removeStorePreference(fakeCpf, idStore, fakeToken)

        verify { repository.removeStorePreference(fakeCpf, idStore, fakeToken) }
        verify { status.onChanged(false) }
    }

    private fun instantiateViewModel(): StoreViewModel {
        val viewModel = StoreViewModel(repository)
        viewModel.status.observeForever(status)
        viewModel.wallet.observeForever(wallet)
        viewModel.storeList.observeForever(listObserver)
        viewModel.errorMessage.observeForever(error)
        return viewModel
    }

}