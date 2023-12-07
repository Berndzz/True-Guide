package com.hardus.trueagencyapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.hardus.trueagencyapp.util.await
import javax.inject.Inject

class AuthFirebaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthFirebase {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                result?.user?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                )?.await()
                savePhoneNumberToDatabase(result?.user?.uid, phoneNumber)
                Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    private suspend fun savePhoneNumberToDatabase(userId: String?, phoneNumber: String) {
        try {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users")
            userId?.let {
                databaseReference.child(it).child("phone").setValue(phoneNumber).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}