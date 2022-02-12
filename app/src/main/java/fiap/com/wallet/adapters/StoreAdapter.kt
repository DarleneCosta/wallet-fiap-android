package fiap.com.wallet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import fiap.com.wallet.R
import fiap.com.wallet.databinding.ResItemPreferenceBinding
import fiap.com.wallet.models.StorePreference
import fiap.com.wallet.models.UserSession
import fiap.com.wallet.viewmodel.storePreference.StoreViewModel


class StoreAdapter(private val onItemClicked: (StorePreference) -> Unit) :RecyclerView.Adapter<StoreViewHolder>() {
    private var stores = mutableListOf<StorePreference>()

    fun setStorePreferenceList(store: List<StorePreference>) {
        this.stores = store.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResItemPreferenceBinding.inflate(inflater, parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        holder.bind(store, onItemClicked)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

}


class StoreViewHolder(private val binding: ResItemPreferenceBinding) :RecyclerView.ViewHolder(binding.root) {

    fun bind(store: StorePreference,  onItemClicked: (StorePreference) -> Unit) {

        //TODO: colocar o icone da loja
        binding.name.text = store.name
        binding.percent.text = store.percent.toString() + "%"

        binding.btnDelete.setOnClickListener {
            onItemClicked(store)
        }
    }



}

