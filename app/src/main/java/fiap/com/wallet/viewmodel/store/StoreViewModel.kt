package fiap.com.wallet.viewmodel.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.repositories.StoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel constructor(private val repository: StoreRepository):ViewModel() {
    val storeList = MutableLiveData<List<StorePreference>>()
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
}