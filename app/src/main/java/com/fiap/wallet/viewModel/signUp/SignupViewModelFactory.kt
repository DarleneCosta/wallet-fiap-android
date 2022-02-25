package com.fiap.wallet.viewModel.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.repositories.signUp.SignupRepository

class SignupViewModelFactory constructor(private val repository: SignupRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            SignupViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("SignupViewModel Not Found")
        }
    }

}