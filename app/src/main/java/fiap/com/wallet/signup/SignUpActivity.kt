package fiap.com.wallet.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.R
import fiap.com.wallet.databinding.ActivitySignupBinding
import fiap.com.wallet.login.LoginActivity
import fiap.com.wallet.login.rest.RetrofitService
import fiap.com.wallet.signup.model.SignUp
import fiap.com.wallet.signup.repository.SignUpRepository
import fiap.com.wallet.signup.viewmodel.SignUpViewModel
import fiap.com.wallet.signup.viewmodel.SignUpViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_signup)

        viewModel =
            ViewModelProvider(this, SignUpViewModelFactory(SignUpRepository(retrofitService))).get(
                SignUpViewModel::class.java
            )

        observeViewModel()

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            val nome = findViewById<EditText>(R.id.txtNome).text.toString()
            var email = findViewById<EditText>(R.id.txtEmail).text.toString()
            var cpf = findViewById<EditText>(R.id.txtCpf).text.toString()
            var password = findViewById<EditText>(R.id.txtSenha).text.toString()
            var switch = findViewById<Switch>(R.id.swtNotification).isChecked()

            viewModel.signUp(SignUp(nome, email, cpf, password, switch))
        }
    }

    private fun observeViewModel() {
        viewModel.liveDataSignUp.observe(this, Observer { response ->
            if (response == 200) {
                Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Erro no servidor, tente novamente", Toast.LENGTH_SHORT).show()
            }
        })
    }
}