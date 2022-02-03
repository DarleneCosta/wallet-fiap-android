package fiap.com.wallet.login.rest

import fiap.com.wallet.BuildConfig
import fiap.com.wallet.login.dto.LoginDTO
import fiap.com.wallet.login.dto.LoginResponseDTO
import fiap.com.wallet.login.dto.SignUpDTO
import fiap.com.wallet.login.models.SignUp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("/user/signup")
    fun signUp(signUp: SignUp): Call<SignUpDTO>

    @POST("/login")
    fun login(@Body loginDTO: LoginDTO): Call<LoginResponseDTO>

    companion object {
        private val BASE_URL = BuildConfig.HOST
        val retrofitService: RetrofitService by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build().create(RetrofitService::class.java)
        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }
    }

}