package com.zachtib.bookmarks.ui.bookmarklist

import androidx.lifecycle.ViewModel
import com.zachtib.bookmarks.service.BookmarksService

class BookmarkListViewModel(private val service: BookmarksService) : ViewModel() {
    fun getBookmarks() = service.getAllBookmarks()

    suspend fun onStart() {
        service.populateDatabase()
    }
}
