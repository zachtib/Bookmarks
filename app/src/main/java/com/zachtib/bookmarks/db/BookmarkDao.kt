package com.zachtib.bookmarks.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zachtib.bookmarks.db.models.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): LiveData<List<Bookmark>>

    @Insert
    suspend fun insertAll(vararg bookmarks: Bookmark)

    @Query("DELETE FROM bookmarks WHERE 1")
    suspend fun deleteAll()
}