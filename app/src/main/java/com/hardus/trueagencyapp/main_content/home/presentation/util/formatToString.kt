package com.hardus.trueagencyapp.main_content.home.presentation.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun Date.formatToString(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    return sdf.format(this)
}

fun LocalDate.toTimestamp(): Timestamp {
    val instant = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Timestamp(Date.from(instant))
}