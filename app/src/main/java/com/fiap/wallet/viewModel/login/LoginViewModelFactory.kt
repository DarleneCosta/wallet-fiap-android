package com.fiap.wallet.viewModel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.repositories.login.LoginRepository

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("LoginViewModel Not Found")
        }
    }
}