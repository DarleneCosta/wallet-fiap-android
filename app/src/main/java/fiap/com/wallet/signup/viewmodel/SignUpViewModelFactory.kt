package fiap.com.wallet.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.login.dto.viewmodel.LoginViewModel
import fiap.com.wallet.login.repository.LoginRepository
import fiap.com.wallet.signup.repository.SignUpRepository

class SignUpViewModelFactory (private val repository: SignUpRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}