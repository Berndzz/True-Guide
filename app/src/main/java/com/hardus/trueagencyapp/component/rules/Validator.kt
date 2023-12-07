package com.hardus.trueagencyapp.component.rules

object Validator {
    fun validateUsername(username: String): ValidateResult {
        return ValidateResult(
            (!username.isNullOrEmpty() && username.length >= 2)
        )
    }

    fun validateEmail(email: String): ValidateResult {
        return ValidateResult(
            (!email.isNullOrEmpty()),
        )
    }

    fun validatePhoneNumber(phoneN: String): ValidateResult {
        return ValidateResult(
            (!phoneN.isNullOrEmpty())
        )
    }

    fun validatePassword(password: String): ValidateResult {
        return ValidateResult(
            (!password.isNullOrEmpty() && password.length >= 4),
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidateResult {
        return ValidateResult(
            statusValue
        )
    }
}

data class ValidateResult(
    val status: Boolean = false,
)