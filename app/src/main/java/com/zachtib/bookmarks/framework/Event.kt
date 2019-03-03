package com.zachtib.bookmarks.framework

data class Event<T>(val value: T) {
    private var isHandled = false

    fun peek() = value

    fun handle(handler: (T) -> Unit) {
        if (!isHandled) {
            handler(value)
            isHandled = true
        }
    }
}