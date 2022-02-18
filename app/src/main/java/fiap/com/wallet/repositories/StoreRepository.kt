package fiap.com.wallet.repositories

import fiap.com.wallet.rest.RetroService

class StoreRepository constructor(private val retroService: RetroService){

    fun getAllStorePreference(cpf: String, token: String) = retroService.getAllStorePreference(cpf, token)
    fun removeStorePreference(cpf:String,id:Int,token: String) = retroService.removeStorePreference(cpf, id, token)


}