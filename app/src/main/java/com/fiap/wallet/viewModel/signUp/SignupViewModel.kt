package com.fiap.wallet.viewModel.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiap.wallet.models.SignUp
import com.fiap.wallet.repositories.signUp.SignupRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody
import java.net.HttpURLConnection


class SignupViewModel constructor(private val repository: SignupRepository) : ViewModel() {

    val status = MutableLiveData<Boolean>()

    fun signUp (signUp:SignUp){

        val request = repository.signUp(signUp)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    status.postValue(true)
                } else {
                    status.postValue(false)
                }
            }

            override fun onFailure(call:Call<ResponseBody>, t:Throwable) {
                status.postValue(false)
            }
        })
    }
}