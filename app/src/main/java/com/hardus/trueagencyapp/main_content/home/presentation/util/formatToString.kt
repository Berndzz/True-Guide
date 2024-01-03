package com.hardus.trueagencyapp.main_content.home.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToString(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    return sdf.format(this)
}