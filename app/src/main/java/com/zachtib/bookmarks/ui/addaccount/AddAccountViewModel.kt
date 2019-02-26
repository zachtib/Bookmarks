package com.zachtib.bookmarks.ui.addaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zachtib.bookmarks.BookmarksPreferences
import com.zachtib.bookmarks.framework.mutableLiveDataOf
import com.zachtib.bookmarks.service.BookmarksService
import timber.log.Timber

class AddAccountViewModel(
    private val preferences: BookmarksPreferences,
    private val service: BookmarksService
) : ViewModel() {

    private val _loginButtonEnabled = mutableLiveDataOf(false)
    val loginButtonEnabled: LiveData<Boolean> = _loginButtonEnabled

    private val _loadingIndicatorVisible = mutableLiveDataOf(false)
    val loadingIndicatorVisible: LiveData<Boolean> = _loadingIndicatorVisible

    var serverUrl: String = ""
        set(value) {
            field = value
            validateInput()
        }
    var username: String = ""
        set(value) {
            field = value
            validateInput()
        }
    var password: String = ""
        set(value) {
            field = value
            validateInput()
        }

    private fun validateInput() {
        val enabled = serverUrl.isNotBlank()
                && username.isNotBlank()
                && password.isNotBlank()
        _loginButtonEnabled.postValue(enabled)
    }

    suspend fun loginButtonClicked(): Boolean {
        _loadingIndicatorVisible.value = true
        try {
            service.authenticate(serverUrl, username, password)
            return true
        } catch (e: Exception) {
            Timber.e(e)
        }
        _loadingIndicatorVisible.value = false
        return false
    }
}
