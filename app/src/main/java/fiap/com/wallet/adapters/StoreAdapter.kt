package fiap.com.wallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fiap.com.wallet.databinding.ResItemPreferenceBinding
import fiap.com.wallet.models.Store


class StoreAdapter(private val onItemClicked: (Store) -> Unit) :RecyclerView.Adapter<StoreViewHolder>() {
    private var stores = mutableListOf<Store>()

    fun setStorePreferenceList(store: List<Store>) {
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

    fun bind(store: Store, onItemClicked: (Store) -> Unit) {

        //TODO: colocar o icone da loja
        binding.name.text = store.name
        binding.percent.text = store.percent.toString() + "%"

        binding.btnDelete.setOnClickListener {
            onItemClicked(store)
        }
    }
}

