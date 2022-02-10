package fiap.com.wallet.repositories

import fiap.com.wallet.rest.RetroService
import fiap.com.wallet.models.SignUp

class SignupRepository constructor(private val retrofitService: RetroService) {

    fun signUp(signUp: SignUp) = retrofitService.signUp(signUp)

}