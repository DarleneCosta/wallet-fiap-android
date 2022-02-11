package fiap.com.wallet.wallet

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fiap.com.wallet.R
import fiap.com.wallet.databinding.ActivityWalletBinding


class WalletActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_wallet)

        val intent = this.intent
        val value = intent.getStringExtra("CPF")

        val view = findViewById<View>(R.id.textView2) as TextView
        view.text = value
    }
}