package fiap.com.wallet.store

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.R
import fiap.com.wallet.databinding.ActivityStoreBinding
import fiap.com.wallet.store.adapters.StoreAdapter
import fiap.com.wallet.store.api.RetrofitService
import fiap.com.wallet.store.models.StorePreference
import fiap.com.wallet.store.repositories.StoreRepository
import fiap.com.wallet.store.store.StoreViewModel
import fiap.com.wallet.store.store.StoreViewModelFactory


class StoreActivity : AppCompatActivity() {

    lateinit var viewModel: StoreViewModel

    private lateinit var binding: ActivityStoreBinding

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = StoreAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, StoreViewModelFactory(StoreRepository(retrofitService))).get(
                StoreViewModel::class.java
            )
        binding.recyclerview.adapter = adapter

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
        viewModel.getAllStore()
    }

    private fun createStorePreference(store: StorePreference) {
        val cpf = "11111111111"
        viewModel.addStorePreference(cpf, store.id)
    }


}