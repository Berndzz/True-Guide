package com.hardus.trueagencyapp.firebase

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?
)