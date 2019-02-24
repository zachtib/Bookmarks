package com.zachtib.bookmarks.api

sealed class ServerResponse<out T>(val status: String) {
    class One<out T>(status: String, val item: T) : ServerResponse<T>(status)
    class Many<out T>(status: String, val data: List<T>) : ServerResponse<T>(status)
    class Error(error: Throwable) : ServerResponse<Nothing>("error")
    object Disconnected : ServerResponse<Nothing>("disconnected")
}