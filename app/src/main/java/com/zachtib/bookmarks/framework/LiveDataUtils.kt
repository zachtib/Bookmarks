package com.zachtib.bookmarks.framework

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveDataOf(initial: T) = MutableLiveData<T>().apply {
    value = initial
}

fun <T, R> MutableLiveData<T>.setWhile(temporaryValue: T, job: () -> R): R {
    val previousValue = this.value
    this.value = temporaryValue
    try {
        return job()
    } finally {
        this.value = previousValue
    }
}