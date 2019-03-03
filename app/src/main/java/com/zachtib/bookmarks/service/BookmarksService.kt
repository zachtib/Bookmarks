package com.zachtib.bookmarks.service

import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.zachtib.bookmarks.BookmarksPreferences
import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.db.models.Account
import com.zachtib.bookmarks.db.models.Bookmark
import com.zachtib.bookmarks.work.RefreshDatabase
import timber.log.Timber

class BookmarksService(private val db: BookmarksDatabase, private val prefs: BookmarksPreferences) {

    private val allBookmarks = db.bookmarkDao().liveAllBookmarks()

    fun getAllBookmarks() = allBookmarks

    private val bookmarksByAccount = mutableMapOf<Account, LiveData<List<Bookmark>>>()

    fun getBookmarksForAccount(account: Account): LiveData<List<Bookmark>> {
        return bookmarksByAccount.getOrPut(account) {
            db.bookmarkDao().liveBookmarksForAccountId(account.id)
        }
    }

    suspend fun authenticate(serverUrl: String, username: String, password: String): Boolean {
        if (db.accountDao().getAccountByServerAndUsername(serverUrl, username) != null) {
            Timber.w("Tried to log into an account that already exists")
            return false
        }
        val api = BookmarksApiProvider.get(serverUrl, username, password)
        val response = try {
            api.getBookmarks()
        } catch (e: Exception) {
            Timber.w(e, "Got exception from api")
            ServerResponse.Error(e)
        }
        if (response is ServerResponse.Error) {
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

}