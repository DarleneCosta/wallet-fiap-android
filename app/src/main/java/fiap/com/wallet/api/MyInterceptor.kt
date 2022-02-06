package fiap.com.wallet.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
        .newBuilder()
        .addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTExMTExMTExMSIsImV4cCI6MTY0NTAyOTgwMX0.CQBpCf3rrkZflG50vWiQ-ccvnM8cLDFZEvZG2E_rTOqzR3_VE6h-uU2LWNThwoVTmFh127iBf_BkdRxfd4djwQ")
        .build()

        return chain.proceed(request)

    }

}