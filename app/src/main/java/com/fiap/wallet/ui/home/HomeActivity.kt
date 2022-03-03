package com.fiap.wallet.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wallet.R
import com.fiap.wallet.utils.SharedSession
import com.fiap.wallet.ui.login.LoginActivity
import com.fiap.wallet.ui.signUp.SignUpActivity
import com.fiap.wallet.ui.store.StoreActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }

    override fun onStart() {
        super.onStart()

        val session = SharedSession(this).getSession()
        if(!session.cpf.isNullOrEmpty()){
            startActivity(Intent(this@HomeActivity, StoreActivity::class.java))
            finish()
        }

    }

    fun login(v: View) {
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
    }

    fun register(v: View?) {
        startActivity(Intent(this@HomeActivity, SignUpActivity::class.java))
    }

}