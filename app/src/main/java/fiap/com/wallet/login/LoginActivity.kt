package fiap.com.wallet.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.R
import fiap.com.wallet.databinding.ActivityLoginBinding
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.dto.viewmodel.LoginViewModel
import fiap.com.wallet.login.dto.viewmodel.LoginViewModelFactory
import fiap.com.wallet.login.repository.LoginRepository
import fiap.com.wallet.login.rest.RetrofitService
import fiap.com.wallet.wallet.WalletActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        viewModel =
            ViewModelProvider(this, LoginViewModelFactory(LoginRepository(retrofitService))).get(
                LoginViewModel::class.java
            )

        observeLoginViewModel()
    }

    private fun observeLoginViewModel() {
        viewModel.liveDataSignUp.observe(this, Observer { loginResponseDTO ->
            if (loginResponseDTO == null) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            } else {
                salvarToken(loginResponseDTO.token)
                Toast.makeText(this, "Logado com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WalletActivity::class.java)
                intent.putExtra("CPF", loginResponseDTO.cpf);
                startActivity(intent)
                finish()
            }
        })
    }

    fun login(view: View) {
        val cpf = findViewById<EditText>(R.id.cpf).text.toString();
        val password = findViewById<EditText>(R.id.edt_password).text.toString();

        viewModel.login(LoginDTO(cpf, password))

    }


    fun salvarToken(token: String) {
        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE).edit()
        sharedPreferences.putString("token", token)
        sharedPreferences.commit()
    }

}