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


class StoreAdapter(private var context: Context) :RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    private var stores = mutableListOf<StorePreference>()

    fun setStorePreferenceList(store: List<StorePreference>) {
        this.stores = store.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResItemPreferenceBinding.inflate(inflater, parent, false)
        //val view = inflater.inflate(R.layout.res_item_preference, parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    class StoreViewHolder(private val binding: ResItemPreferenceBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(store: StorePreference) {

            //TODO: colocar o icone da loja
            binding.name.text = store.name
            binding.percent.text = store.percent.toString() + "%"

            binding.btnDelete.setOnClickListener {

                val msg = "Você deseja excluir a loja " + store.name + "?"
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle(R.string.title_delete)
                builder.setMessage(msg)

                builder.setPositiveButton("Sim"){_, _ ->
                    deleteStoreFavorite(store, it.context)
                }
                builder.setNeutralButton("Cancelar") { _, _ ->
                    println("nok")
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
        }

        private fun deleteStoreFavorite(
            store: StorePreference,
            context: Context
        ) {
           val session = UserSession(context)
            val cpf = session.getStr("cpf")
            val token = session.getStr("token")

            if (!cpf.isNullOrEmpty() && !token.isNullOrEmpty() ) {

                //viewModel.removeStorePreference(cpf, store.id ,"Bearer $token")
            }
        }


    }
}

