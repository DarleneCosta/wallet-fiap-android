package fiap.com.wallet.store.repositories

import fiap.com.wallet.store.api.RetrofitService

class StoreRepository constructor(private val retrofitService: RetrofitService){

fun getAllStore() = retrofitService.getAllStore()

}