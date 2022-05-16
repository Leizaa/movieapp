package com.example.tmdb.utility

import com.example.tmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url
        val finalUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val request = chain.request().newBuilder()
            .url(finalUrl)
            .build()

        return chain.proceed(request)
    }
}