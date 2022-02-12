package fiap.com.wallet.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fiap.com.wallet.R
import fiap.com.wallet.models.UserSession

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val session = UserSession(this)
        val cpf = session.getStr("cpf")
        val token = session.getStr("token")
        if(!cpf.isNullOrEmpty() && !token.isNullOrEmpty() ){
            startActivity(Intent(this@HomeActivity, StoreActivity::class.java))
            finish()
        }

    }

    fun login(v:View) {
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        finish()
    }

    fun register(v:View?) {
        startActivity(Intent(this@HomeActivity, SignUpActivity::class.java))
        finish()
    }

}