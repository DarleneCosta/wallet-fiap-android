package fiap.com.wallet.repositories

import fiap.com.wallet.rest.RetroService

class StoreAddRepository constructor(private val retroService: RetroService){

    fun getAllStore(token: String) = retroService.getAllStore(token)
    fun getStore(name:String, token: String) = retroService.getStore(name, token)
    fun addStorePreference(cpf:String,id:Int,token: String)  = retroService.addStorePreference(cpf, id, token)

}