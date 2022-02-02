package fiap.com.wallet.login.rest

import fiap.com.wallet.login.dto.LoginDTO
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
    fun login(@Body loginDTO: LoginDTO): Call<ResponseBody>

    companion object {
        private const val BASE_URL = "http://3.140.201.92"
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