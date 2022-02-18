package fiap.com.wallet.login.dto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.dto.LoginResponseDTO
import fiap.com.wallet.login.repository.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel constructor(private val repository: LoginRepository) : ViewModel() {

    val liveDataSignUp = MutableLiveData<LoginResponseDTO>()

    fun login(login: LoginDTO) {
        val request = repository.login(login)
        request.enqueue(object : Callback<LoginResponseDTO> {
            override fun onResponse(
                call: Call<LoginResponseDTO>,
                response: Response<LoginResponseDTO>
            ) {
                if (response.code() != 200) {
                    liveDataSignUp.postValue(null)
                    return;
                } else {
                    var body = response.body()
                    if (body != null) {
                        liveDataSignUp.postValue(LoginResponseDTO(body.token, body.cpf))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                liveDataSignUp.postValue(null)
                return;
            }

        })
    }
}