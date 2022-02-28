package com.fiap.wallet.models

data class User(
    var id: Int,
    var name: String,
    var birthDate: String,
    var email: String,
    var cpf: String,
    var cellphone: String
)
