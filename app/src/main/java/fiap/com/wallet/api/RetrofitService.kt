package fiap.com.wallet.api
import fiap.com.wallet.models.StorePreference
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {

    @GET("preference/11111111111")//TODO: CPF COMO PEGAR ESTA INFORMAÇÃO
    fun getAllStore(): Call<List<StorePreference>>


    companion object{
        private val client=OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor())
        }.build()

        private val retrofitService: RetrofitService by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://34.134.40.101:8080")//TODO: USAR VARIAVEL GLOBAL
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