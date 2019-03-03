package com.zachtib.bookmarks.api

import timber.log.Timber

suspend fun <T, R> T.apiCall(call: suspend T.() -> ServerResponse<R>): ServerResponse<R> {
    return try {
        call()
    } catch (e: Exception) {
        Timber.w(e, "Error during api call")
        ServerResponse.Error(e)
    }
}