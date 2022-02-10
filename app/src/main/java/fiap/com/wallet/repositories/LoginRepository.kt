package fiap.com.wallet.repositories

import fiap.com.wallet.models.LoginRequest
import fiap.com.wallet.rest.RetroService


class LoginRepository constructor(private val retrofitService: RetroService) {

    fun login(loginRequest: LoginRequest) = retrofitService.login(loginRequest)

}