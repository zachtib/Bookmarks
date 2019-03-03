package com.zachtib.bookmarks.usecase

import com.zachtib.bookmarks.db.models.Account

interface SyncBookmarksForAccount {
    suspend operator fun invoke(account: Account): Boolean
}