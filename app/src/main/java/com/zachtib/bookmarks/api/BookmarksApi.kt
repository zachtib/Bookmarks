package com.zachtib.bookmarks.api

import com.zachtib.bookmarks.models.Bookmark
import retrofit2.http.GET

interface BookmarksApi {
    @GET("bookmark")
    suspend fun getBookmarks(): ServerResponse.Many<Bookmark>
}