package fiap.com.wallet.rest

import fiap.com.wallet.store.api.MyInterceptor
import fiap.com.wallet.store.api.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetroFitService {
    companion object{
        private val client= OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor())
        }.build()

        private val retrofitService: RetrofitService by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://3.140.201.92")//TODO: USAR VARIAVEL GLOBAL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance (): RetrofitService {
            return retrofitService
        }
    }
}