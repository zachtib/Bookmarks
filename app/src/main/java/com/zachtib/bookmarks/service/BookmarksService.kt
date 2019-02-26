package com.zachtib.bookmarks.service

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.zachtib.bookmarks.BookmarksPreferences
import com.zachtib.bookmarks.api.BookmarksApi
import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.db.models.Account
import com.zachtib.bookmarks.work.RefreshDatabase
import timber.log.Timber

class BookmarksService(private val db: BookmarksDatabase, private val prefs: BookmarksPreferences) {

    private val allBookmarks = db.bookmarkDao().getAllBookmarks()

    fun getAllBookmarks() = allBookmarks

    private var api: BookmarksApi? = null

    private suspend fun <T> apiCall(doApiCall: suspend BookmarksApi.() -> ServerResponse<T>): ServerResponse<T> {
        return api?.doApiCall() ?: ServerResponse.Disconnected
    }

    suspend fun authenticate(serverUrl: String, username: String, password: String): Boolean {
        if (db.accountDao().getAccountByServerAndUsername(serverUrl, username) != null) {
            Timber.w("Tried to log into an account that already exists")
            return false
        }
        api = BookmarksApiProvider.get(serverUrl, username, password)
        if (!isConnected()) {
            api = null
            Timber.w("Failed to connect to server $serverUrl")
            return false
        }

        prefs.serverUrl = serverUrl
        prefs.username = username
        prefs.password = password

        val newAccount = Account(0, serverUrl, username, password)
        db.accountDao().insert(newAccount)

        val work = OneTimeWorkRequestBuilder<RefreshDatabase>().build()
        WorkManager.getInstance().enqueue(work)
        return true
    }

    suspend fun getAccounts(): List<Account> {
        return db.accountDao().getAllAccounts()
    }


    suspend fun isConnected(): Boolean {
        val result = apiCall { getBookmarks() }
        return result !is ServerResponse.Disconnected
    }

}