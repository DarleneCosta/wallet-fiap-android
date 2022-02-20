package com.fiap.wallet.viewModel.storeAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.repositories.storeAdd.StoreAddRepository

class StoreAddViewModelFactory constructor(private val repository: StoreAddRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(StoreAddViewModel::class.java)){
            StoreAddViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("StoreViewModel Not Found")
        }
    }
}