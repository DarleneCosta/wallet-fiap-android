package fiap.com.wallet.viewmodel.storePreference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.repositories.StoreRepository

class StoreViewModelFactory constructor(private val repository: StoreRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(StoreViewModel::class.java)){
            StoreViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("StoreViewModel Not Found")
        }
    }
}