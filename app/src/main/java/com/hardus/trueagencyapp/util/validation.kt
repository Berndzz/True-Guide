package com.hardus.trueagencyapp.util

fun validateEmail(email: String): Boolean {
    // Validasi email menggunakan regex
    val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    return regex.matches(email)
}

fun validatePassword(password: String): Boolean {
    // Validasi password minimal 6 karakter
    return password.length >= 6
}

fun validatePhoneNumber(phoneNumber: String): Boolean {
    // Validasi nomor telepon menggunakan regex
    val regex = Regex("^[0-9]{10,13}$")
    return regex.matches(phoneNumber)
}

fun validateUsername(username: String): Boolean {
    // Validasi username menggunakan regex
    val regex = Regex("^[a-zA-Z0-9_]{2,20}$")
    return regex.matches(username)
}

fun getPhoneNumberError(phoneNumber: String): String =
    if (phoneNumber.isEmpty()) "Required"
    else if (phoneNumber.length >= 14) "Nomor anda tidak valid"
    else ""


fun getUsernameError(username: String): String =
    if (username.isEmpty()) "Required"
    else if (username.length >= 15) "Username anda terlalu panjang"
    else ""

fun getEmailError(email: String): String =
    if (email.isEmpty()) "Required" else if (!Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matches(
            email
        )
    ) "Email tidak valid" else ""


fun getPasswordError(password: String): String =
    if (password.isEmpty()) "Required" else if (password.length < 6) "Password minimal 6 karakter" else ""