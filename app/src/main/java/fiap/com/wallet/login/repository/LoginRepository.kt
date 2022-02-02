package fiap.com.wallet.login.repository

import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.rest.RetrofitService

class LoginRepository constructor(val retrofitService: RetrofitService) {

    //fun signUp(signUp: SignUp) = retrofitService.signUp(signUp)
    fun login(login: LoginDTO) = retrofitService.login(login)
}