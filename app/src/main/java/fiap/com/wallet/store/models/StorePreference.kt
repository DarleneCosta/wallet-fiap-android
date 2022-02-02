package fiap.com.wallet.store.models

data class StorePreference(
    var id: Int,
    var name: String,
    var  cnpj: String,
    var  percent: Long
)
