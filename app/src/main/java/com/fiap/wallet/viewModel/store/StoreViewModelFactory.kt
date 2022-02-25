package com.fiap.wallet.viewModel.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.repositories.store.StoreRepository

class StoreViewModelFactory constructor(private val repository: StoreRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            StoreViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("StoreViewModel Not Found")
        }
    }
}