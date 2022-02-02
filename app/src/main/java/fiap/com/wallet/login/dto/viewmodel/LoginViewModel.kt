package fiap.com.wallet.login.dto.viewmodel

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.repository.LoginRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel constructor(private val repository: LoginRepository) : ViewModel() {

    val liveDataSignUp = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun login(login: LoginDTO) {
        val request = repository.login(login)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() != 200) {
                    liveDataSignUp.postValue("")
                    return
                }
                liveDataSignUp.postValue(response.headers().get("Authorization"))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}