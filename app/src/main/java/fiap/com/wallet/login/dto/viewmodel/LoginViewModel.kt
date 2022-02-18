package fiap.com.wallet.login.dto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.models.LoginRequest
import fiap.com.wallet.models.LoginResponse
import fiap.com.wallet.repositories.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel constructor(private val repository: LoginRepository) : ViewModel() {

    val liveDataSignUp = MutableLiveData<LoginResponse>()

    fun login(login: LoginRequest) {
        val request = repository.login(login)
        request.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() != 200) {
                    liveDataSignUp.postValue(null)
                    return;
                } else {
                    var body = response.body()
                    if (body != null) {
                        liveDataSignUp.postValue(LoginResponse(body.token, body.cpf))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                liveDataSignUp.postValue(null)
                return;
            }

        })
    }
}