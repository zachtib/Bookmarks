package com.zachtib.bookmarks.ui.bookmarklist

import androidx.lifecycle.ViewModel
import com.zachtib.bookmarks.models.Bookmark
import com.zachtib.bookmarks.service.BookmarksService

class BookmarkListViewModel(private val service: BookmarksService) : ViewModel() {
    suspend fun getBookmarks(): List<Bookmark> {
        return service.getBookmarks()
    }
}
