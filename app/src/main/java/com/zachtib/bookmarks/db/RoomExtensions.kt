package com.zachtib.bookmarks.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

inline fun <reified T : RoomDatabase> roomDatabaseBuilder(context: Context, name: String) = Room.databaseBuilder(context, T::class.java, name)