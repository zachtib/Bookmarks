package com.zachtib.bookmarks.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val serverId: Int,
    val accountId: Int,
    val url: String,
    val title: String
)