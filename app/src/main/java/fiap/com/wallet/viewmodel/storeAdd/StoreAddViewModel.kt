package fiap.com.wallet.viewmodel.storeAdd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.models.Store
import fiap.com.wallet.repositories.StoreAddRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class StoreAddViewModel(private val repository: StoreAddRepository): ViewModel() {
    val storeList = MutableLiveData<List<Store>>()
    val store = MutableLiveData<Store>()
    val status = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getAllStore (token:String){
        val request = repository.getAllStore( token)
        request.enqueue(object : Callback<List<Store>> {
            override fun onResponse(
                call: Call<List<Store>>,
                response: Response<List<Store>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    storeList.postValue(response.body())
                } else {
                    errorMessage.postValue("Erro ao listar lojas  ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getStore (name:String,token:String){
        val request = repository.getStore(name, token)
        request.enqueue(object : Callback<Store> {
            override fun onResponse(
                call: Call<Store>,
                response: Response<Store>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    store.postValue(response.body())
                } else {
                    errorMessage.postValue("Erro ao lista loja ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Store>, t: Throwable) {
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


}