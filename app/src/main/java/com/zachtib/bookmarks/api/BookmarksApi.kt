package com.zachtib.bookmarks.api

import com.zachtib.bookmarks.models.Bookmark
import retrofit2.Call
import retrofit2.http.GET

interface BookmarksApi {
    @GET("bookmark")
    fun getBookmarks(): Call<ServerResponse.Many<Bookmark>>
}