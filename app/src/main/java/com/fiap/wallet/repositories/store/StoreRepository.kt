package com.fiap.wallet.repositories.store

import com.fiap.wallet.rest.RetroService

class StoreRepository constructor(private val retroService: RetroService) {

    fun getWallet(cpf: String, token: String) =
        retroService.getWallet(cpf, token)

    fun getAllStorePreference(cpf: String, token: String) =
        retroService.getAllStorePreference(cpf, token)

    fun removeStorePreference(cpf: String, id: Int, token: String) =
        retroService.removeStorePreference(cpf, id, token)

}