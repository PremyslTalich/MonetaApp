package cz.talich.ma.library.datanba.data

import cz.talich.ma.library.datanba.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder().apply {
            header("Authorization", BuildConfig.BALLDONTLIE_API_KEY)
        }

        return chain.proceed(requestBuilder.build())
    }
}