package com.fiap.wallet.repositories.signUp

import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.models.SignUp

class SignupRepository constructor(private val retrofitService: RetroService) {

    fun signUp(signUp: SignUp) = retrofitService.signUp(signUp)

}