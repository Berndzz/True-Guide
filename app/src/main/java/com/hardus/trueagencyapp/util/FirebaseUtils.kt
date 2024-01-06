package com.hardus.trueagencyapp.util

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

object FirestoreService {
    @SuppressLint("StaticFieldLeak")
    val db = FirebaseFirestore.getInstance()
}

object FirestoreAuth {
    val db = FirebaseAuth.getInstance()
}

@OptIn(ExperimentalCoroutinesApi::class)
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