package com.zachtib.bookmarks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zachtib.bookmarks.db.models.Bookmark

@Database(entities = arrayOf(Bookmark::class), version = 1)
abstract class BookmarksDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
