package fiap.com.wallet.store.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
<<<<<<< HEAD:app/src/main/java/fiap/com/wallet/store/store/StoreViewModel.kt
import fiap.com.wallet.store.models.StorePreference
import fiap.com.wallet.store.repositories.StoreRepository
=======
import fiap.com.wallet.models.ResponseWallet
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.repositories.StoreRepository
>>>>>>> 5e2c3bdf96f582677918b77f4b4ecf83e1ab75a8:app/src/main/java/fiap/com/wallet/viewmodel/store/StoreViewModel.kt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel constructor(private val repository: StoreRepository):ViewModel() {
    val storeList = MutableLiveData<List<StorePreference>>()
    val responseWallet = MutableLiveData<ResponseWallet?>()
    val errorMessage = MutableLiveData<String>()

    fun getAllStore (){
        val request = repository.getAllStore()
        request.enqueue(object :Callback<List<StorePreference>>{
            override fun onResponse(
                call: Call<List<StorePreference>>,
                response: Response<List<StorePreference>>
            ) {
                storeList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<StorePreference>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

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
    }

}
