package fiap.com.wallet.viewmodel.storePreference

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.repositories.StoreRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK

class StoreViewModel constructor(private val repository: StoreRepository):ViewModel() {
    val storeList = MutableLiveData<List<StorePreference>>()
    val status = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllStore (cpf:String,token:String){
        val request = repository.getAllStore(cpf, token)
        request.enqueue(object : Callback<List<StorePreference>> {
            override fun onResponse(
                call: Call<List<StorePreference>>,
                response: Response<List<StorePreference>>
            ) {
                if (response.code() == HTTP_OK) {
                    storeList.postValue(response.body())
                } else {
                    errorMessage.postValue("Erro ao listar lojas favoritas ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<StorePreference>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


    fun addStorePreference(cpf:String, id:Int,token:String){
        val request = repository.addStorePreference(cpf, id, token)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    status.postValue(true)
                } else {
                    errorMessage.postValue("Erro ao adicionar loja a sua lista de favoritas ${response.code()}")
                    status.postValue(false)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false)
            }
        })

    }

    fun removeStorePreference(cpf:String, id:Int,token:String){
        val request = repository.removeStorePreference(cpf, id, token)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    status.postValue(true)
                } else {
                    errorMessage.postValue("Erro ao remover loja de sua lista de favoritas ${response.code()}")
                    status.postValue(false)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false)
            }
        })

    }

}
