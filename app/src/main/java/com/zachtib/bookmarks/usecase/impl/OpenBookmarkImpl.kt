package com.zachtib.bookmarks.usecase.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.zachtib.bookmarks.db.models.Bookmark
import com.zachtib.bookmarks.usecase.OpenBookmarkUseCase

class OpenBookmarkImpl(private val context: Context) : OpenBookmarkUseCase {
    override fun invoke(bookmark: Bookmark) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        browserIntent.data = Uri.parse(bookmark.url)
        context.startActivity(browserIntent)
    }
}