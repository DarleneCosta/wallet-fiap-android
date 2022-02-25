package com.fiap.wallet.ui.store

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.wallet.R
import com.fiap.wallet.ui.store.adapters.StoreAdapter
import com.fiap.wallet.databinding.ActivityStoreBinding
import com.fiap.wallet.models.Store
import com.fiap.wallet.utils.Session
import com.fiap.wallet.repositories.store.StoreRepository
import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.ui.home.HomeActivity
import com.fiap.wallet.ui.storeAdd.StoreAddActivity
import com.fiap.wallet.viewModel.store.*


class StoreActivity : AppCompatActivity() {

    lateinit var viewModel: StoreViewModel
    private lateinit var binding: ActivityStoreBinding
    private val retroService = RetroService.getInstance()
    private val adapter = StoreAdapter { store ->
        confirmDeleteStore(store)
    }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMenu.setOnClickListener {
            onAddButtonClicked()
        }
        binding.btnExit.setOnClickListener {
            logout()
        }
        binding.btnAdd.setOnClickListener {
            addStore()
        }

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
        loadingListStorePreference()
    }

    private fun addStore() {
        startActivity(Intent(this@StoreActivity, StoreAddActivity::class.java))
    }

    private fun confirmDeleteStore(store: Store) {
        val msg = "Você deseja excluir a loja ${store.name}?"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_delete)
        builder.setMessage(msg)

        builder.setPositiveButton("Sim") { _, _ ->

            binding.loadingView.show()
            val session = Session(this)
            val cpf = session.getStr("cpf")
            val token = session.getStr("token")

            if (!cpf.isNullOrEmpty() && !token.isNullOrEmpty()) {
                viewModel.removeStorePreference(cpf, store.id, "Bearer $token")
                loadingListStorePreference()
            }
        }
        builder.setNeutralButton("Cancelar") { _, _ ->
            println("nok")
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun loadingListStorePreference() {
        val session = Session(this)
        val cpf = session.getStr("cpf")
        val token = session.getStr("token")

        if (!cpf.isNullOrEmpty() && !token.isNullOrEmpty()) {
            viewModel.getAllStore(cpf, "Bearer $token")
            binding.loadingView.dismiss()
        }
    }

    private fun logout() {
        val session = Session(this)
        session.clearAll()
        startActivity(Intent(this@StoreActivity, HomeActivity::class.java))
        finish()
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.btnAdd.visibility = View.VISIBLE
            binding.btnExit.visibility = View.VISIBLE
        } else {
            binding.btnAdd.visibility = View.INVISIBLE
            binding.btnExit.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.btnAdd.startAnimation(fromBottom)
            binding.btnExit.startAnimation(fromBottom)
            binding.btnMenu.startAnimation(rotateOpen)
        } else {
            binding.btnAdd.startAnimation(toBottom)
            binding.btnExit.startAnimation(toBottom)
            binding.btnMenu.startAnimation(rotateClose)
        }
    }

}