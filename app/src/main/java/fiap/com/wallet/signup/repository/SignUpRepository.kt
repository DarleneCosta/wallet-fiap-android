package fiap.com.wallet.signup.repository

import fiap.com.wallet.login.rest.RetrofitService
import fiap.com.wallet.signup.model.SignUp

class SignUpRepository constructor(val retrofitService: RetrofitService) {

    fun signUp(signUp: SignUp) = retrofitService.signUp(signUp)
}