package com.zachtib.bookmarks.service

import com.zachtib.bookmarks.db.models.Account
import com.zachtib.bookmarks.db.models.Bookmark
import okhttp3.OkHttpClient

class BookmarkThumbnailDownloader(
    private val account: Account,
    private val httpClient: OkHttpClient
) {

    suspend fun downloadThumbnailFor(bookmark: Bookmark) {
        val url = account.serverUrl + "/apps/bookmarks/bookmark/" + bookmark.id + "/image"
    }
}