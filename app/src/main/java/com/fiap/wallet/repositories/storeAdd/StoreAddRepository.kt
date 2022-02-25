package com.fiap.wallet.repositories.storeAdd

import com.fiap.wallet.rest.RetroService

class StoreAddRepository constructor(private val retroService: RetroService) {

    fun getAllStore(token: String) = retroService.getAllStore(token)
    fun getStoreSearch(name: String, token: String) = retroService.getStoreSearch(name, token)
    fun addStorePreference(cpf: String, id: Int, token: String) =
        retroService.addStorePreference(cpf, id, token)

}