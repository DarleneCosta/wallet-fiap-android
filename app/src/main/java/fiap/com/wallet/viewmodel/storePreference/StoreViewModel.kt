package fiap.com.wallet.viewmodel.storePreference

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.repositories.StoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class StoreViewModel constructor(private val repository: StoreRepository):ViewModel() {
    val storeList = MutableLiveData<List<StorePreference>>()
    //val responseWallet = MutableLiveData<ResponseWallet?>()
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
/*
    fun addStorePreference(cpf:String, id:Int){
        val request = repository.addStorePreference(cpf, id)
        request.enqueue(object :Callback<ResponseWallet?>{
            override fun onResponse(
                call: Call<ResponseWallet?>,
                response: Response<ResponseWallet?>
            ) {
                if(response.isSuccessful){
                    responseWallet.postValue(response.body())
                }else{
                    responseWallet.postValue(null)
                }
            }
            override fun onFailure(call: Call<ResponseWallet?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun removeStorePreference(cpf:String, id:Int){
        val request = repository.removeStorePreference(cpf, id)
        request.enqueue(object :Callback<ResponseWallet?>{
            override fun onResponse(
                call: Call<ResponseWallet?>,
                response: Response<ResponseWallet?>
            ) {
                if(response.isSuccessful){
                    responseWallet.postValue(response.body())
                }else{
                    responseWallet.postValue(null)
                }
            }
            override fun onFailure(call: Call<ResponseWallet?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }*/

}
