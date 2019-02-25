package com.zachtib.bookmarks.ui.bookmarklist

import androidx.lifecycle.ViewModel
import com.zachtib.bookmarks.db.models.Bookmark
import com.zachtib.bookmarks.service.BookmarksService
import com.zachtib.bookmarks.usecase.OpenBookmarkUseCase

class BookmarkListViewModel(
    private val service: BookmarksService,
    private val openBookmark: OpenBookmarkUseCase
) : ViewModel() {
    fun getBookmarks() = service.getAllBookmarks()

    suspend fun shouldRedirectToAddAccount(): Boolean {
        return service.getAccounts().isEmpty()
    }

    fun onBookmarkClicked(bookmark: Bookmark) {
        openBookmark(bookmark)
    }

    suspend fun onStart() {
        service.populateDatabase()
    }
}
