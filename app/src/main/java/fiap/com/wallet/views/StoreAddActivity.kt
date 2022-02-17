package fiap.com.wallet.views

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.databinding.ActivityStoreAddBinding
import fiap.com.wallet.models.Session
import fiap.com.wallet.models.Store
import fiap.com.wallet.repositories.StoreAddRepository
import fiap.com.wallet.rest.RetroService
import fiap.com.wallet.viewmodel.storeAdd.StoreAddViewModel
import fiap.com.wallet.viewmodel.storeAdd.StoreAddViewModelFactory


class StoreAddActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityStoreAddBinding
    private lateinit var viewModel: StoreAddViewModel
    lateinit var storeAdapter : ArrayAdapter<String>
    private var retrofitService = RetroService.getInstance()

    var cpf = String()
    var token = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoreAddBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel =
            ViewModelProvider(this, StoreAddViewModelFactory(StoreAddRepository(retrofitService))).get(
                StoreAddViewModel::class.java
            )

        val session = Session(this)
        token = session.getStr("token").toString()
        cpf = session.getStr("cpf").toString()

    }

    override fun onResume() {
        super.onResume()
        _binding.loadingView.show()
        viewModel.getAllStore( "Bearer $token")
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
                loadingStore((arg1 as TextView).text.toString())
                _binding.cardView.visibility = View.VISIBLE
            }
        _binding.listView.onItemClickListener = listClickItem
    }

    private fun loadingStore(name: String){
        _binding.loadingView.show()
        viewModel.getStore( name, "Bearer $token")
        viewModel.store.observe(this, Observer { store ->
            println(store)
            _binding.loadingView.dismiss()
            _binding.txtStore.text = store.name
            _binding.txtPercent.text = store.percent.toString() + "%"
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
        Toast.makeText(baseContext, "add info", Toast.LENGTH_LONG).show()
    }

    fun back(v: View){
        startActivity(Intent(this@StoreAddActivity, StoreActivity::class.java))
        finish()
    }

}