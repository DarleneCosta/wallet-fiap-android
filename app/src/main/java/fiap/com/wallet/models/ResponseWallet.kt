package fiap.com.wallet.models

import java.util.*

data class ResponseWallet(
    val id: Int?,
    val user: User?,
    val value: Double?,
    val partnerStore: Objects?,
    val score: String?
)
