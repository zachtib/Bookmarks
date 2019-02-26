package com.zachtib.bookmarks.framework

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun Call.await() = suspendCoroutine<ResponseBody> { continuation ->
    this.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.body()?.let { body ->
                continuation.resume(body)
            }
            continuation.resumeWithException(NullPointerException("ResponseBody was null"))
        }
    })
}

suspend fun Call.awaitResponse() = suspendCoroutine<Response> { continuation ->
    this.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
            continuation.resume(response)
        }
    })
}