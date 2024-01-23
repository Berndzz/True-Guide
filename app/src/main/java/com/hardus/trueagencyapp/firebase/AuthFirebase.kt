package com.hardus.trueagencyapp.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthFirebase {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String
    ): Resource<FirebaseUser>

    suspend fun sendPasswordResetEmail(email: String): Resource<String>

    fun logout()
}