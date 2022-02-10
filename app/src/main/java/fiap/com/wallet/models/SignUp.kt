package fiap.com.wallet.models

data class SignUp(
    var name: String,
    var email: String,
    var cpf: String,
    var password: String,
    var sendNotification: Boolean
)
