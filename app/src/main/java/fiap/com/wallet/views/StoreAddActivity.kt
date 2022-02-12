package fiap.com.wallet.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fiap.com.wallet.R

class StoreAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_add)
    }

    fun close(v: View){
        startActivity(Intent(this@StoreAddActivity, StoreActivity::class.java))
        finish()
    }

}