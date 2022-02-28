package com.fiap.wallet.models

data class Wallet(
    var id: Int,
    var user: User,
    var value: Double
)
