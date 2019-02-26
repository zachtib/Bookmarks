package com.zachtib.bookmarks.framework

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Call<T>.await() = suspendCoroutine<T> { continuation ->
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) = continuation.resumeWithException(t)

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val body = response.body()
            if (body == null) {
                continuation.resumeWithException(Exception("Got back a null body"))
            } else {
                continuation.resume(body)
            }
        }
    })
}