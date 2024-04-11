package com.hardus.trueagencyapp.firebase

import android.content.Intent
import com.google.firebase.auth.FirebaseUser

interface AuthFirebase {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun register(
        name: String, email: String, phoneNumber: String, password: String
    ): Resource<FirebaseUser>

    suspend fun sendPasswordResetEmail(email: String): Resource<String>

    fun logout()
}