package fiap.com.wallet.repositories

import fiap.com.wallet.rest.RetroService

class StoreRepository constructor(private val retroService: RetroService){

    fun getAllStore(cpf: String,token: String) = retroService.getAllStore(cpf, token)
    //fun removeStorePreference(cpf:String,id:Int) = retroService.removeStorePreference(cpf,id)
    //fun addStorePreference(cpf:String,id:Int) = retroService.removeStorePreference(cpf,id)

}