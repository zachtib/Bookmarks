package com.zachtib.bookmarks

import android.content.Context
import androidx.room.Room
import com.zachtib.bookmarks.db.BookmarksDatabase
import com.zachtib.bookmarks.db.createDatabase
import com.zachtib.bookmarks.service.BookmarksService
import com.zachtib.bookmarks.ui.addaccount.AddAccountViewModel
import com.zachtib.bookmarks.ui.bookmarklist.BookmarkListViewModel
import com.zachtib.bookmarks.usecase.OpenBookmarkUseCase
import com.zachtib.bookmarks.usecase.impl.OpenBookmarkImpl
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { get<Context>().getSharedPreferences("bookmarks-prefs", Context.MODE_PRIVATE) }
    single { BookmarksPreferences(get()) }
    single { BookmarksService(get(), get()) }
    single { createDatabase<BookmarksDatabase>(get(), "bookmarks-db") }

    // Use cases
    single<OpenBookmarkUseCase> { OpenBookmarkImpl(get()) }

    // ViewModels
    viewModel { AddAccountViewModel(get(), get()) }
    viewModel { BookmarkListViewModel(get(), get()) }
}