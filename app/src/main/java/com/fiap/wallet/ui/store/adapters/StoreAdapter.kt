package com.fiap.wallet.ui.store.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fiap.wallet.R
import com.fiap.wallet.databinding.ResItemPreferenceBinding
import com.fiap.wallet.models.Store


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

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(store.urlLogo)
            .into(binding.logo)

        binding.btnDelete.setOnClickListener {
            onItemClicked(store)
        }
    }
}

