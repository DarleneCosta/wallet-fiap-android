package com.fiap.wallet.utils

object Validator {


    fun validateEmail(email: String): Boolean {
        return !(email.isEmpty() || email.isBlank() || !email.contains("@"))
    }

    fun validatePassword(password: String): Boolean {
        return !(password.isEmpty() || password.isBlank())
    }

    fun validateName(name: String): Boolean {
        return !(name.isEmpty() || name.isBlank())
    }

    fun validateCPF(cpf: String): Boolean {
        return !(cpf.isEmpty() || cpf.isBlank() || cpf.length!==11)
    }

}