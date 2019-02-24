package com.zachtib.bookmarks.service

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.zachtib.bookmarks.api.BookmarksApi
import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.api.models.Account
import com.zachtib.bookmarks.api.models.Bookmark
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.work.RefreshDatabase

class BookmarksService(private val db: BookmarksDatabase) {

    private val allBookmarks = db.bookmarkDao().getAllBookmarks()

    fun getAllBookmarks() = allBookmarks

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

    suspend fun populateDatabase() {
        val work = OneTimeWorkRequestBuilder<RefreshDatabase>().build()
        WorkManager.getInstance().enqueue(work)
    }

    suspend fun isConnected(): Boolean {
        val result = apiCall { getBookmarks() }
        return result !is ServerResponse.Disconnected
    }

    suspend fun getBookmarks(): List<Bookmark> {
        val result = apiCall { getBookmarks() }
        return when (result) {
            is ServerResponse.Many -> result.data
            else -> listOf()
        }
    }
}