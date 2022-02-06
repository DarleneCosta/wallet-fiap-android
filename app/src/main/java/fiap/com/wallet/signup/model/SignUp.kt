package fiap.com.wallet.signup.model

import java.time.LocalDate

data class SignUp(
    var name: String,
    var email: String,
    var cpf: String,
    var password: String,
    var sendNotification: Boolean
)
