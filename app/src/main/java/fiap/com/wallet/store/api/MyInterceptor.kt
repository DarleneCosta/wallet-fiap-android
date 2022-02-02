package fiap.com.wallet.store.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
        .newBuilder()
        .addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0NDU1NiIsImV4cCI6MTY0NDQwNzYzOX0.GIEj9AgHVnOWQ8CP78OcoWaxljaFnKwwQn-gMwKJu-IlHvmPKa1pDQgSQ86QJpjjLL5xua9VdiAvnNIyqfzTMg")
        .build()

        return chain.proceed(request)

    }

}