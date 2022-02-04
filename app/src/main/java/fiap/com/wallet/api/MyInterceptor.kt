package fiap.com.wallet.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
        .newBuilder()
        .addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJleHAiOjE2NDQ4NzI5NzJ9.M7wJXELZTM21hWPsxgqXp5LdrwSALP3ZHgxmACBZ4vT4AJaLbtHfEpieVJ1wFnXLprLd3-eAkM1TyEi4Yg9o-g")
        .build()

        return chain.proceed(request)

    }

}