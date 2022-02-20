package com.fiap.wallet.models

import java.time.LocalDate

data class User (
    var id: Int,
    var name: String,
    var birthDate: LocalDate,
    var email: String,
    var cpf: String,
    var cellphone: String
)
