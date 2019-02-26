package com.zachtib.bookmarks.framework

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

inline fun <reified T : RoomDatabase> createDatabase(
        context: Context,
        name: String,
        extras: RoomDatabase.Builder<T>.() -> Unit = {}): T {
    val builder = Room.databaseBuilder(context, T::class.java, name)
    builder.extras()
    return builder.build()
}
