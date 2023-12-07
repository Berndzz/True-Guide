package com.hardus.trueagencyapp.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { count ->
        addOnCompleteListener {
            if (it.exception != null) {
                count.resumeWithException(it.exception!!)
            } else {
                count.resume(it.result, null)
            }
        }
    }
}