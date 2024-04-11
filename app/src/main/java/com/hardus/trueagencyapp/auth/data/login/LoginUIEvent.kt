package com.hardus.trueagencyapp.auth.data.login

sealed class LoginUIEvent {
    object LoginButtonClicked : LoginUIEvent()
}