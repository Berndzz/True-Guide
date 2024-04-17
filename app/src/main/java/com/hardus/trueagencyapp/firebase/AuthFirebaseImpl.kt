package com.hardus.trueagencyapp.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.hardus.trueagencyapp.util.await
import javax.inject.Inject

class AuthFirebaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
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
            val user = result.user!!
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            saveUserToFirestore(user.uid, name, phoneNumber)
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


    override suspend fun sendPasswordResetEmail(email: String): Resource<String> {
        return try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            Resource.Success("Email sent successfully")
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    private suspend fun saveUserToFirestore(userId: String, name: String, phone: String) {
        try {
            val userMap = hashMapOf(
                "id_user" to userId,
                "name_user" to name,
                "phone_user" to phone
            )

            FirebaseFirestore.getInstance().collection("profil").document(userId)
                .set(userMap)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override suspend fun updatePhotoUrl(photoUrl: String) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(photoUrl))
                .build()

            user.updateProfile(profileUpdates).await()
        } else {
            throw Exception("User not logged in")
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}