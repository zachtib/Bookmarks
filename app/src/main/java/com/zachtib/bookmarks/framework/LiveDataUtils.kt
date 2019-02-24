package com.zachtib.bookmarks.framework

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveDataOf(initial: T) = MutableLiveData<T>().apply {
    value = initial
}