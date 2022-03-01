package fiap.com.wallet.rest

import fiap.com.wallet.store.api.MyInterceptor
import fiap.com.wallet.store.api.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 interface  RetroFitService {

    companion object{
        private const val BASE_URL = "http://34.134.40.101:8080"

        private val retrofitService: RetrofitService by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }

         fun getInstance (): RetrofitService {
            return retrofitService
        }

    }

}