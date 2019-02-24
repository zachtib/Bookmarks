package com.zachtib.bookmarks.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val serverUrl: String,
    val username: String,
    val password: String
)