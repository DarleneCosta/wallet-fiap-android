package com.fiap.wallet.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.databinding.ActivityLoginBinding
import com.fiap.wallet.models.LoginRequest
import com.fiap.wallet.utils.SharedSession
import com.fiap.wallet.repositories.login.LoginRepository
import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.ui.signUp.SignUpActivity
import com.fiap.wallet.ui.store.StoreActivity
import com.fiap.wallet.utils.Validator.validateCPF
import com.fiap.wallet.utils.Validator.validatePassword
import com.fiap.wallet.viewModel.login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val retrofitService = RetroService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel =
            ViewModelProvider(this, LoginViewModelFactory(LoginRepository(retrofitService))).get(
                LoginViewModel::class.java
            )

        setupUi()
    }


    private fun setupUi() = _binding.apply {

        btnLogin.setOnClickListener {

            if (!validateCPF(txtCpf.text.toString())) {
                txtCpf.error = "Preencha o cpf corretamente"
                txtCpf.requestFocus()
                return@setOnClickListener
            }

            if (!validatePassword(txtSenha.text.toString())) {
                txtSenha.error = "Preencha a senha de acesso"
                txtSenha.requestFocus()
                return@setOnClickListener

            }

            viewModel.login(
                LoginRequest(
                    txtCpf.text.toString(),
                    txtSenha.text.toString()
                )
            )
            loadingView.show()
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
    }


    override fun onStart() {
        super.onStart()

        viewModel.success.observe(this, Observer {

            val session = SharedSession(this)
            session.setStr("cpf", it.cpf)
            session.setStr("token", it.token)

            startActivity(Intent(this@LoginActivity, StoreActivity::class.java))
            finish()

        })

        viewModel.errorMessage.observe(this, Observer {
            _binding.loadingView.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }
}