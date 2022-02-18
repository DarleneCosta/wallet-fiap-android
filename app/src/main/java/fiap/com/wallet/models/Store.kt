package fiap.com.wallet.models

data class Store(
    var id: Int,
    var name: String,
    var  cnpj: String,
    var  percent: Long,
    var  urlLogo: String
)
