package fiap.com.wallet.store.api
import fiap.com.wallet.models.ResponseWallet
import retrofit2.http.GET
import retrofit2.Call
import fiap.com.wallet.store.models.StorePreference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetrofitService {

    @GET("preference/11111111111")//TODO: CPF COMO PEGAR ESTA INFORMAÇÃO
    @Headers( "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTExMTExMTExMSIsImV4cCI6MTY0NTA5MTE1NX0.TtxikFKh8JwDsSXzqa7-hcf2vulAwaVGnO8AUhtmJ3daU_pfOyIJG_C-p62Hf4Zf4DMiomblKikAXKfqfdOnNA")
    fun getAllStore(): Call<List<StorePreference>>

    @POST("preference/{cpf}/{id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTExMTExMTExMSIsImV4cCI6MTY0NTA5MTE1NX0.TtxikFKh8JwDsSXzqa7-hcf2vulAwaVGnO8AUhtmJ3daU_pfOyIJG_C-p62Hf4Zf4DMiomblKikAXKfqfdOnNA")
    fun addStorePreference(@Path ("cpf")cpf:String, @Path ("id")id:Int): Call<ResponseWallet>


    @DELETE("preference/{cpf}/{id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTExMTExMTExMSIsImV4cCI6MTY0NTA5MTE1NX0.TtxikFKh8JwDsSXzqa7-hcf2vulAwaVGnO8AUhtmJ3daU_pfOyIJG_C-p62Hf4Zf4DMiomblKikAXKfqfdOnNA")
    fun removeStorePreference(@Path ("cpf")cpf:String, @Path ("id")id:Int): Call<ResponseWallet>

    companion object{
        private const val BASE_URL = "http://34.134.40.101:8080"//BuildConfig.HOST

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