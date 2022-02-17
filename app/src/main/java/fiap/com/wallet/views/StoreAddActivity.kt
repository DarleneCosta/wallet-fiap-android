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
    private var retrofitService = RetroService.getInstance()
    lateinit var storeAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoreAddBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel =
            ViewModelProvider(this, StoreAddViewModelFactory(StoreAddRepository(retrofitService))).get(
                StoreAddViewModel::class.java
            )


    }

    override fun onResume() {
        super.onResume()

        val session = Session(this)
        val token = session.getStr("token")

        if (!token.isNullOrEmpty()) {
            _binding.loadingView.show()
            viewModel.getAllStore( "Bearer $token")
        }
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

        val listClickItem =
            OnItemClickListener { _, arg1, _, _ ->
                val info = (arg1 as TextView).text.toString()
                Toast.makeText(baseContext, "Item $info", Toast.LENGTH_LONG).show()
                _binding.cardView.visibility = View.VISIBLE
            }
        _binding.listView.onItemClickListener = listClickItem



    }
    fun cancel(v: View){
        _binding.cardView.visibility = View.GONE
    }
    fun addStore(v: View){
        Toast.makeText(baseContext, "add info", Toast.LENGTH_LONG).show()
    }

    fun close(v: View){
        startActivity(Intent(this@StoreAddActivity, StoreActivity::class.java))
        finish()
    }

}