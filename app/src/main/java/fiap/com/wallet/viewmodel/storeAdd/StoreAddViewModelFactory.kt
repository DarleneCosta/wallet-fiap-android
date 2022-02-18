package fiap.com.wallet.viewmodel.storeAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fiap.com.wallet.repositories.StoreAddRepository

class StoreAddViewModelFactory constructor(private val repository: StoreAddRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(StoreAddViewModel::class.java)){
            StoreAddViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("StoreViewModel Not Found")
        }
    }
}