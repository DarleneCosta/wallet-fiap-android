package fiap.com.wallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fiap.com.wallet.databinding.ResItemFavoriteBinding
import fiap.com.wallet.models.StorePreference

class StoreAdapter(private val onItemClicked: (StorePreference)->Unit) :RecyclerView.Adapter<StoreViewHolder>() {
    private var stores= mutableListOf<StorePreference>()

    fun setStorePreferenceList(store:List<StorePreference>){
        this.stores=store.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResItemFavoriteBinding.inflate(inflater, parent, false)
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


class StoreViewHolder(val binding: ResItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(store: StorePreference, onItemClicked: (StorePreference) -> Unit) {

        binding.name.text = store.name
        binding.percent.text = store.percent.toString()

        itemView.setOnClickListener {
            onItemClicked(store)
        }

    }

}