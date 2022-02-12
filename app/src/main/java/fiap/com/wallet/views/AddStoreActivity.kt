package fiap.com.wallet.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fiap.com.wallet.R

class AddStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)
    }

    fun close(v: View){
        startActivity(Intent(this@AddStoreActivity, StoreActivity::class.java))
        finish()
    }
}