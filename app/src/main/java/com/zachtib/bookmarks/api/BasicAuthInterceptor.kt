package com.zachtib.bookmarks.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val username: String, private val password: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = Credentials.basic(username, password)
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }

}