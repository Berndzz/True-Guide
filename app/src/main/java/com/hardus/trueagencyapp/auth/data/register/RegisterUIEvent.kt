package com.hardus.trueagencyapp.auth.data.register

sealed class RegisterUIEvent {
    object RegisterButtonClicked : RegisterUIEvent()
}