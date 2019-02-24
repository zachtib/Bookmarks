package com.zachtib.bookmarks.ui.login

import androidx.lifecycle.ViewModel;
import com.zachtib.bookmarks.BookmarksPreferences
import com.zachtib.bookmarks.api.models.Account
import com.zachtib.bookmarks.service.BookmarksService

class LoginViewModel(
    private val preferences: BookmarksPreferences,
    private val service: BookmarksService
) : ViewModel() {

    suspend fun tryLogin(serverUrl: String, username: String, password: String): Boolean {
        return service.connect(Account(serverUrl, username, password))
    }
}
