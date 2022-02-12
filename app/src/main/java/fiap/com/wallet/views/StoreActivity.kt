package fiap.com.wallet.views

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.R
import fiap.com.wallet.adapters.StoreAdapter
import fiap.com.wallet.databinding.ActivityStoreBinding
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.models.UserSession
import fiap.com.wallet.repositories.StoreRepository
import fiap.com.wallet.rest.RetroService
import fiap.com.wallet.viewmodel.storePreference.*


class StoreActivity : AppCompatActivity() {

    lateinit var viewModel: StoreViewModel
    private lateinit var binding: ActivityStoreBinding
    private val retroService = RetroService.getInstance()
    private val adapter = StoreAdapter { store ->
        confirmDeleteStore(store)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, StoreViewModelFactory(StoreRepository(retroService))).get(
                StoreViewModel::class.java
            )
        binding.recyclerview.adapter = adapter
        binding.loadingView.show()
    }

    override fun onStart() {
        super.onStart()

        viewModel.storeList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setStorePreferenceList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()
        loadingListStore()
    }

    private fun loadingListStore(){
        val session = UserSession(this)
        val cpf = session.getStr("cpf")
        val token = session.getStr("token")

        if (!cpf.isNullOrEmpty() && !token.isNullOrEmpty() ) {
            viewModel.getAllStore(cpf, "Bearer $token")
            binding.loadingView.dismiss()
        }
    }

    private fun confirmDeleteStore(store:StorePreference){
        val msg = "Você deseja excluir a loja " + store.name + "?"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_delete)
        builder.setMessage(msg)

        builder.setPositiveButton("Sim"){_, _ ->

            binding.loadingView.show()
            val session = UserSession(this)
            val cpf = session.getStr("cpf")
            val token = session.getStr("token")

            if (!cpf.isNullOrEmpty() && !token.isNullOrEmpty() ) {
                viewModel.removeStorePreference(cpf, store.id ,"Bearer $token")
                loadingListStore()
            }
        }
        builder.setNeutralButton("Cancelar") { _, _ ->
            println("nok")
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

   }

    fun addActivity(v: View?) {
        startActivity(Intent(this@StoreActivity, StoreAddActivity::class.java))
        finish()
    }

    fun logout(v: View?) {
        val session =  UserSession(this)
        session.clearAll()
        startActivity(Intent(this@StoreActivity, HomeActivity::class.java))
        finish()
    }

}