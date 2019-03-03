package com.zachtib.bookmarks.usecase.impl

import com.zachtib.bookmarks.api.BookmarksApiProvider
import com.zachtib.bookmarks.api.ServerResponse
import com.zachtib.bookmarks.api.apiCall
import com.zachtib.bookmarks.converters.toApiModel
import com.zachtib.bookmarks.converters.toDbModel
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.db.models.Account
import com.zachtib.bookmarks.db.models.Bookmark
import com.zachtib.bookmarks.framework.collect
import com.zachtib.bookmarks.usecase.SyncBookmarksForAccount

class SyncBookmarksForAccountImpl(
    private val database: BookmarksDatabase,
    private val apiProvider: BookmarksApiProvider
) : SyncBookmarksForAccount {

    override suspend fun invoke(account: Account): Boolean {
        val (accountId, url, username, password) = account
        val api = apiProvider.get(url, username, password)
        val dao = database.bookmarkDao()

        val response = api.apiCall { getBookmarks() } as? ServerResponse.Many ?: return false

        val serverBookmarks: List<Bookmark> = response.data.map { serverBookmark ->
            serverBookmark.toDbModel().copy(accountId = accountId)
        }
        val localBookmarks: List<Bookmark> = dao.getBookmarksForAccountId(accountId)

        val serverBookmarksDict = serverBookmarks.collect { bookmark -> bookmark.serverId }

        for (bookmark in localBookmarks) {
            when {
                bookmark.serverId == 0 -> {
                    // Create it on the server
                    val newBookmark = api.createBookmark(bookmark.toApiModel())
                    val updatedBookmark = bookmark.copy(serverId = newBookmark.item.id)
                    dao.update(updatedBookmark)
                }
                serverBookmarksDict.containsKey(bookmark.serverId) -> {
                    // Update the local copy to reflect the server (for now)
                    serverBookmarksDict[bookmark.serverId]?.let { serverBookmark ->
                        val updatedBookmark = bookmark.copy(
                            title = serverBookmark.title,
                            url = serverBookmark.url
                        )
                        dao.update(updatedBookmark)
                    }
                }
                else -> // Assume deleted on the server, delete it locally
                    dao.delete(bookmark)
            }
        }



        return true
    }
}