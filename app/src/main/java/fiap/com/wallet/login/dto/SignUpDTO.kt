package fiap.com.wallet.login.dto

import java.time.LocalDate

data class SignUpDTO(
    var name: String,
    var birthDate: LocalDate,
    var email: String,
    var cpf: String,
    var cellphone: String,
    var sendNotification: Boolean
)
