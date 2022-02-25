package com.fiap.wallet.ui.signUp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.utils.Validator.validateCPF
import com.fiap.wallet.utils.Validator.validateEmail
import com.fiap.wallet.utils.Validator.validateName
import com.fiap.wallet.utils.Validator.validatePassword
import com.fiap.wallet.databinding.ActivitySignupBinding
import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.models.SignUp
import com.fiap.wallet.repositories.signUp.SignupRepository
import com.fiap.wallet.ui.login.LoginActivity
import com.fiap.wallet.viewModel.signUp.SignupViewModel
import com.fiap.wallet.viewModel.signUp.SignupViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel
    private val retrofitService = RetroService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel =
            ViewModelProvider(this, SignupViewModelFactory(SignupRepository(retrofitService))).get(
                SignupViewModel::class.java
            )

        registerUser()

    }

    private fun registerUser() = _binding.apply {

        btnSignUp.setOnClickListener {

            if (!validateName(txtNome.text.toString())) {
                txtNome.error = "Preencha o nome completo"
                txtNome.requestFocus()
                return@setOnClickListener
            }

            if (!validateEmail(txtEmail.text.toString())) {
                txtEmail.error = "Preencha o email corretamente"
                txtEmail.requestFocus()
                return@setOnClickListener
            }

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

            viewModel.signUp(
                SignUp(
                    txtNome.text.toString(),
                    txtEmail.text.toString(),
                    txtCpf.text.toString(),
                    txtSenha.text.toString(),
                    swtNotification.isChecked()
                )
            )
            loadingView.show()

        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.status.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    "Usuário registrado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao tentar registrar usuário. Tente novamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            _binding.loadingView.dismiss()

        })

    }
}