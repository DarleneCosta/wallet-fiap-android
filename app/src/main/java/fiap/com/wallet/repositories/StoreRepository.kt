package fiap.com.wallet.repositories

import fiap.com.wallet.rest.RetroService

class StoreRepository constructor(private val retroService: RetroService){

    fun getAllStore(cpf: String,token: String) = retroService.getAllStore(cpf, token)
    fun removeStorePreference(cpf:String,id:Int,token: String) = retroService.removeStorePreference(cpf, id, token)
    fun addStorePreference(cpf:String,id:Int,token: String)  = retroService.removeStorePreference(cpf, id, token)

}