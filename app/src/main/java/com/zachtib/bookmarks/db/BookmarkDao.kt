package com.zachtib.bookmarks.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zachtib.bookmarks.db.models.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun liveAllBookmarks(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE accountId=:accountId")
    fun liveBookmarksForAccountId(accountId: Int): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE accountId=:accountId")
    suspend fun getBookmarksForAccountId(accountId: Int): List<Bookmark>

    @Insert
    suspend fun insert(vararg bookmarks: Bookmark)

    @Update
    suspend fun update(vararg bookmarks: Bookmark)

    @Delete
    suspend fun delete(vararg bookmarks: Bookmark)

    @Query("DELETE FROM bookmarks WHERE 1")
    suspend fun deleteAll()
}