package com.zachtib.bookmarks.api

import com.zachtib.bookmarks.api.models.Bookmark
import retrofit2.http.GET
import retrofit2.http.POST

interface BookmarksApi {
    @GET("bookmark")
    suspend fun getBookmarks(): ServerResponse.Many<Bookmark>

    @POST("bookmark")
    fun createBookmark(bookmark: Bookmark): ServerResponse.One<Bookmark>
}