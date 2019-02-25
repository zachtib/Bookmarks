package com.zachtib.bookmarks.usecase

import com.zachtib.bookmarks.db.models.Bookmark

interface OpenBookmarkUseCase {
    operator fun invoke(bookmark: Bookmark)
}