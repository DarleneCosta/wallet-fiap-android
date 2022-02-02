package fiap.com.wallet.login.models

import java.time.LocalDate

data class SignUp(
    var name: String,
    var birthDate: LocalDate,
    var email: String,
    var cpf: String,
    var cellphone: String,
    var password: String,
    var sendNotification: Boolean
)
