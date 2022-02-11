package fiap.com.wallet.login.dto

import java.time.LocalDate

data class SignUpDTO(
    var name: String,
    var email: String,
    var cpf: String,
    var sendNotification: Boolean
)
