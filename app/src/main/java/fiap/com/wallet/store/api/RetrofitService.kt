package fiap.com.wallet.store.api
import retrofit2.http.GET
import retrofit2.Call
import fiap.com.wallet.store.models.StorePreference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitService {

    @GET("preference/44556")//TODO: CPF COMO PEGAR ESTA INFORMAÇÃO
    fun getAllStore(): Call<List<StorePreference>>

    companion object{
        private val client=OkHttpClient.Builder().apply {
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