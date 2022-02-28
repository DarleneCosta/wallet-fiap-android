package com.fiap.wallet.rest

import com.fiap.wallet.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.fiap.wallet.models.*

interface RetroService {

    @POST("user/signup")
    fun signUp(@Body signUp: SignUp): Call<ResponseBody>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("store")
    fun getAllStore(@Header("Authorization") authorization: String): Call<List<Store>>

    @GET("store/name/{name}")
    fun getStoreSearch(
        @Path("name") cpf: String,
        @Header("Authorization") authorization: String
    ): Call<List<Store>>

    @GET("wallet/{cpf}")
    fun getWallet(
        @Path("cpf") cpf: String,
        @Header("Authorization") authorization: String
    ): Call<Wallet>

    @GET("preference/{cpf}")
    fun getAllStorePreference(
        @Path("cpf") cpf: String,
        @Header("Authorization") authorization: String
    ): Call<List<Store>>

    @POST("preference/{cpf}/{id}")
    fun addStorePreference(
        @Path("cpf") cpf: String,
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    @DELETE("preference/{cpf}/{id}")
    fun removeStorePreference(
        @Path("cpf") cpf: String,
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    companion object {
        private const val BASE_URL = BuildConfig.HOST
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