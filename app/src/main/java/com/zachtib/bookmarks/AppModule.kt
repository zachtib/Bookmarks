package com.zachtib.bookmarks

import android.content.Context
import androidx.room.Room
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.service.BookmarksService
import com.zachtib.bookmarks.ui.bookmarklist.BookmarkListViewModel
import com.zachtib.bookmarks.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { get<Context>().getSharedPreferences("bokmarks-prefs", Context.MODE_PRIVATE) }
    single { BookmarksPreferences(get()) }
    single { BookmarksService(get()) }
    single { Room.databaseBuilder(get(), BookmarksDatabase::class.java, "bookmarks-db").build() }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { BookmarkListViewModel(get()) }
}