package com.zachtib.bookmarks.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zachtib.bookmarks.BookmarksPreferences
import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.api.models.Bookmark
import com.zachtib.bookmarks.converters.toDbModel
import com.zachtib.bookmarks.db.BookmarksDatabase
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class RefreshDatabase(context: Context, params: WorkerParameters)
    : CoroutineWorker(context, params), KoinComponent {

    private val db: BookmarksDatabase by inject()
    private val prefs: BookmarksPreferences by inject()

    override suspend fun doWork(): Result {
        Timber.d("${this.javaClass.simpleName} started")
        val api = BookmarksApiProvider.get(prefs.serverUrl, prefs.username, prefs.password)
        val response: ServerResponse<Bookmark> = api.getBookmarks()
        if (response is ServerResponse.Many) {
            val bookmarks = response.data.map { it.toDbModel() }.toTypedArray()
            db.bookmarkDao().deleteAll()
            db.bookmarkDao().insert(*bookmarks)
        }
        return Result.success()
    }
}