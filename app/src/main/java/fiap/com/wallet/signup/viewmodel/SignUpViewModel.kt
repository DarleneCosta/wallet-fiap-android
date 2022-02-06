package fiap.com.wallet.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.dto.LoginResponseDTO
import fiap.com.wallet.login.dto.SignUpDTO
import fiap.com.wallet.signup.model.SignUp
import fiap.com.wallet.signup.repository.SignUpRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel constructor(private val repository: SignUpRepository) : ViewModel() {

    val liveDataSignUp = MutableLiveData<Int>()

    fun signUp(signUp: SignUp) {
        val request = repository.signUp(signUp)
        request.enqueue(object : Callback<SignUpDTO> {
            override fun onResponse(call: Call<SignUpDTO>, response: Response<SignUpDTO>) {
                liveDataSignUp.postValue(response.code())
            }

            override fun onFailure(call: Call<SignUpDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}