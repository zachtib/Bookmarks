package com.zachtib.bookmarks.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object BookmarksApiProvider {
    private const val apiSuffix = "/index.php/apps/bookmarks/public/rest/v2/"

    fun get(url: String, username: String, password: String): BookmarksApi {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(username, password))
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url + apiSuffix)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}