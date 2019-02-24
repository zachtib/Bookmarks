package com.zachtib.bookmarks.service

import com.zachtib.bookmarks.api.BookmarksApi
import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.api.await
import com.zachtib.bookmarks.models.Account
import com.zachtib.bookmarks.models.Bookmark
import java.lang.IllegalArgumentException

class BookmarksService {

    private var api: BookmarksApi? = null

    private suspend fun <T> apiCall(doApiCall: suspend BookmarksApi.() -> ServerResponse<T>): ServerResponse<T> {
        return api?.doApiCall() ?: ServerResponse.Disconnected
    }

    suspend fun connect(account: Account): Boolean {
        val (serverUrl, username, password) = account
        try {
            api = BookmarksApiProvider.get(serverUrl, username, password)
            if (!isConnected()) {
                api = null
                return false
            }
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }

    suspend fun isConnected(): Boolean {
        val result = apiCall { getBookmarks().await() }
        return result !is ServerResponse.Disconnected
    }

    suspend fun getBookmarks(): List<Bookmark> {
        val result = apiCall { getBookmarks().await() }
        return when (result) {
            is ServerResponse.One -> listOf()
            is ServerResponse.Many -> result.data
            ServerResponse.Disconnected -> listOf()
        }
    }
}