package fiap.com.wallet.repositories

import fiap.com.wallet.api.RetrofitService

class StoreRepository constructor(private val retrofitService: RetrofitService){

fun getAllStore() = retrofitService.getAllStore()

}