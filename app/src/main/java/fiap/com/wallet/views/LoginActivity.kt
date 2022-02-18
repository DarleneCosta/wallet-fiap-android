package fiap.com.wallet.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.databinding.ActivityLoginBinding
import fiap.com.wallet.models.LoginRequest
import fiap.com.wallet.models.Session
import fiap.com.wallet.repositories.LoginRepository
import fiap.com.wallet.rest.RetroService
import fiap.com.wallet.utils.Validator.validateCPF
import fiap.com.wallet.utils.Validator.validatePassword
import fiap.com.wallet.viewmodel.login.*

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

        viewModel.liveDataSignUp.observe(this, Observer {

            if (this != null) {
                //save cpf in preferences
                val session = Session(this)
                session.setStr("cpf", it.cpf)
                session.setStr("token", it.token)

                startActivity(Intent(this@LoginActivity, StoreActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Login ou senha inválida", Toast.LENGTH_SHORT)
            }
        })

    }
}