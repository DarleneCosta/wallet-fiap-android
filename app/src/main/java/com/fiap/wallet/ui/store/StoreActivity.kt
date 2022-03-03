package com.fiap.wallet.ui.store

import android.annotation.SuppressLint
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
import com.fiap.wallet.databinding.ActivityStoreBinding
import com.fiap.wallet.models.Session
import com.fiap.wallet.models.Store
import com.fiap.wallet.repositories.store.StoreRepository
import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.ui.home.HomeActivity
import com.fiap.wallet.ui.store.adapters.StoreAdapter
import com.fiap.wallet.ui.storeAdd.StoreAddActivity
import com.fiap.wallet.utils.SharedSession
import com.fiap.wallet.viewModel.store.StoreViewModel
import com.fiap.wallet.viewModel.store.StoreViewModelFactory
import java.lang.String

class StoreActivity : AppCompatActivity() {

    lateinit var viewModel: StoreViewModel
    private lateinit var _binding: ActivityStoreBinding
    private val retroService = RetroService.getInstance()
    private val adapter = StoreAdapter { store ->
        confirmDeleteStore(store)
    }

    private lateinit var session: Session
    private var notViewWallet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        _binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnMenu.setOnClickListener {
            onAddButtonClicked()
        }
        _binding.btnExit.setOnClickListener {
            logout()
        }
        _binding.btnAdd.setOnClickListener {
            addStore()
        }
        _binding.btnViewValue.setOnClickListener {
            getViewWallet()
        }

        viewModel =
            ViewModelProvider(this, StoreViewModelFactory(StoreRepository(retroService)))[StoreViewModel::class.java]

        _binding.recyclerview.adapter = adapter

        _binding.loadingView.show()

        checkSession()

        notViewWallet = SharedSession(this).getBool("view") == true
        setViewWallet()
        loadingWallet()

    }

    override fun onResume() {
        super.onResume()

        checkSession()
        if(!session.cpf.isNullOrEmpty()) {
            loadingListStorePreference()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadingWallet() {
        _binding.loadingView.show()
        viewModel.getWallet(session.cpf, session.authorization)
        viewModel.wallet.observe(this){
            _binding.txtOla.text = "Olá, ${it.user.name}"
            val balance = String.format("%.2f", it.value)
            var formatView = balance.replace(".", ",")
            _binding.txtWalletView.text = formatView
            _binding.loadingView.dismiss()
        }
        viewModel.errorMessage.observe(this) {
            _binding.loadingView.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun loadingListStorePreference()  {
        _binding.loadingView.show()
        viewModel.getAllStore(session.cpf, session.authorization)

        viewModel.storeList.observe(this) {
            Log.d(TAG, "onCreate: $it")
            adapter.setStorePreferenceList(it)
            _binding.loadingView.dismiss()
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            _binding.loadingView.dismiss()
        }
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
            _binding.loadingView.show()
            viewModel.removeStorePreference(session.cpf, store.id, session.authorization)
            viewModel.status.observe(this, Observer {
                loadingListStorePreference()
                _binding.loadingView.dismiss()
            })
            viewModel.errorMessage.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                _binding.loadingView.dismiss()
            }
        }
        builder.setNeutralButton("Cancelar") { _, _ ->
            println("nok")
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun logout() {
        val session = SharedSession(this)
        session.clearAll()
        startActivity(Intent(this@StoreActivity, HomeActivity::class.java))
        finish()
    }

    private fun getViewWallet(){
        notViewWallet = !notViewWallet
        val session = SharedSession(this)
        session.setBool("view", notViewWallet)
        setViewWallet()
    }

    private fun setViewWallet(){
        if(notViewWallet){
            _binding.txtWalletView.visibility = View.INVISIBLE
            _binding.txtWalletNotView.visibility = View.VISIBLE
        }
        else
        {
            _binding.txtWalletView.visibility = View.VISIBLE
            _binding.txtWalletNotView.visibility = View.INVISIBLE
        }
    }

    private fun checkSession(){
        session = SharedSession(this).getSession()
        if(session.cpf.isNullOrEmpty()){
            logout()
        }
    }

    //button animate
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

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            _binding.btnAdd.visibility = View.VISIBLE
            _binding.btnExit.visibility = View.VISIBLE
        } else {
            _binding.btnAdd.visibility = View.INVISIBLE
            _binding.btnExit.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            _binding.btnAdd.startAnimation(fromBottom)
            _binding.btnExit.startAnimation(fromBottom)
            _binding.btnMenu.startAnimation(rotateOpen)
        } else {
            _binding.btnAdd.startAnimation(toBottom)
            _binding.btnExit.startAnimation(toBottom)
            _binding.btnMenu.startAnimation(rotateClose)
        }
    }

}