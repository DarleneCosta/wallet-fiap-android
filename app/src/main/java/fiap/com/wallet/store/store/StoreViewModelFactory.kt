package fiap.com.wallet.store.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
<<<<<<< HEAD:app/src/main/java/fiap/com/wallet/store/store/StoreViewModelFactory.kt
import fiap.com.wallet.store.repositories.StoreRepository
import java.lang.IllegalArgumentException
=======
import fiap.com.wallet.repositories.StoreRepository
>>>>>>> 5e2c3bdf96f582677918b77f4b4ecf83e1ab75a8:app/src/main/java/fiap/com/wallet/viewmodel/store/StoreViewModelFactory.kt

class StoreViewModelFactory constructor(private val repository: StoreRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(StoreViewModel::class.java)){
            StoreViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("ViewStore Not Found")
        }
    }
}