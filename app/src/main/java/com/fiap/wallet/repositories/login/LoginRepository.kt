package com.fiap.wallet.repositories.login

import com.fiap.wallet.models.LoginRequest
import com.fiap.wallet.rest.RetroService


class LoginRepository constructor(private val retrofitService: RetroService) {

    fun login(loginRequest: LoginRequest) = retrofitService.login(loginRequest)

}