package fiap.com.wallet.store.repositories

import fiap.com.wallet.store.api.RetrofitService

class StoreRepository constructor(private val retrofitService: RetrofitService){

    fun getAllStore() = retrofitService.getAllStore()
    fun removeStorePreference(cpf:String,id:Int) = retrofitService.removeStorePreference(cpf,id)
    fun addStorePreference(cpf:String,id:Int) = retrofitService.removeStorePreference(cpf,id)

}