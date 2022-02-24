package com.fiap.wallet.ui.storeAdd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fiap.wallet.databinding.ActivityStoreAddBinding
import com.fiap.wallet.utils.Session
import com.fiap.wallet.models.Store
import com.fiap.wallet.repositories.storeAdd.StoreAddRepository
import com.fiap.wallet.rest.RetroService
import com.fiap.wallet.ui.stores.StoreActivity
import com.fiap.wallet.viewModel.storeAdd.StoreAddViewModel
import com.fiap.wallet.viewModel.storeAdd.StoreAddViewModelFactory


class StoreAddActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityStoreAddBinding
    private lateinit var viewModel: StoreAddViewModel
    lateinit var storeAdapter : ArrayAdapter<String>
    private var retrofitService = RetroService.getInstance()

    var cpf = String()
    var authorization = String()
    var idStore: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoreAddBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel =
            ViewModelProvider(this, StoreAddViewModelFactory(StoreAddRepository(retrofitService))).get(
                StoreAddViewModel::class.java
            )

        val session = Session(this)
        authorization = "Bearer ${session.getStr("token").toString()}"
        cpf = session.getStr("cpf").toString()

    }

    override fun onResume() {
        super.onResume()
        _binding.loadingView.show()
        viewModel.getAllStore( authorization)
    }

    override fun onStart() {
        super.onStart()

        viewModel.storeList.observe(this, Observer { store ->
            _binding.loadingView.dismiss()
            setStoreList(store)
        })

        viewModel.errorMessage.observe(this, Observer {
            _binding.loadingView.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            setStoreList(listOf())
        })

    }

    private fun setStoreList(store: List<Store>){

        val storeNames: List<String> = store.map { it.name }
        storeAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, storeNames)
        _binding.listView.adapter = storeAdapter

        _binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                _binding.searchView.clearFocus()
                if(storeNames.contains(query)){
                    storeAdapter.filter.filter(query)
                }else{
                    Toast.makeText(applicationContext, "Loja não localizada", Toast.LENGTH_LONG).show()
                }
                println(storeAdapter.count)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                storeAdapter.filter.filter(newText)
                return false
            }
        })

        val listClickItem = OnItemClickListener { _, arg1, _, _ ->
                _binding.loadingView.show()
                loadingStore((arg1 as TextView).text.toString())
            }
        _binding.listView.onItemClickListener = listClickItem
    }

    private fun loadingStore(name: String){

        viewModel.getStoreSearch( name, authorization)
        viewModel.store.observe(this, Observer { store ->

            val requestOptions = RequestOptions()
                .placeholder(com.fiap.wallet.R.drawable.ic_launcher_background)
                .error(com.fiap.wallet.R.drawable.ic_launcher_background)

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(store[0].urlLogo)
                .into(_binding.logo)
            idStore= store[0].id
            _binding.txtStore.text = store[0].name
            _binding.txtPercent.text = store[0].percent.toString() + "%"

            _binding.cardView.visibility = View.VISIBLE
            _binding.loadingView.dismiss()
        })
        viewModel.errorMessage.observe(this, Observer {
            _binding.loadingView.dismiss()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    fun cancel(v: View){
        _binding.cardView.visibility = View.GONE
    }

    fun addStore(v: View){
        idStore?.let { viewModel.addStorePreference(cpf, it, authorization) }
        _binding.loadingView.show()
        back(v)
    }

    fun back(v: View){
        startActivity(Intent(this@StoreAddActivity, StoreActivity::class.java))
        finish()
    }

}