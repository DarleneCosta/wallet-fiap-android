package fiap.com.wallet.rest

import fiap.com.wallet.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import fiap.com.wallet.models.*

interface RetroService {

    @POST("user/signup")
    fun signUp(@Body signUp: SignUp): Call<ResponseBody>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("preference/{cpf}")
    fun getAllStore(@Path ("cpf")cpf:String, @Header("Authorization") authorization: String): Call<List<StorePreference>>

    @POST("preference/{cpf}/{id}")
    fun addStorePreference(@Path ("cpf")cpf:String, @Path ("id")id:Int, @Header("Authorization") authorization: String): Call<ResponseBody>

    @DELETE("preference/{cpf}/{id}")
    fun removeStorePreference(@Path ("cpf")cpf:String,  @Path ("id")id:Int, @Header("Authorization") authorization: String): Call<ResponseBody>

    companion object {
        private const val BASE_URL = "http://34.134.40.101:8080"
        private val retroService: RetroService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetroService::class.java)

        }

        fun getInstance(): RetroService {
            return retroService
        }
    }

}