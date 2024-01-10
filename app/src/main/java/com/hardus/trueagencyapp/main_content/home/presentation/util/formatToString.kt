package com.hardus.trueagencyapp.main_content.home.presentation.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.formatToString(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toJsonString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun isWithinAbsenceWindow(currentTime: Date, startTime: Date, endTime: Date): Boolean {
    val calendarStart = Calendar.getInstance().apply {
        time = startTime
        add(Calendar.HOUR_OF_DAY, -1)  // Satu jam sebelum acara
    }
    val calendarEnd = Calendar.getInstance().apply {
        time = endTime
        add(Calendar.HOUR_OF_DAY, 1)  // Satu jam setelah acara
    }

    return currentTime.after(calendarStart.time) && currentTime.before(calendarEnd.time)
}

fun LocalDate.toTimestamp(): Timestamp {
    val instant = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Timestamp(Date.from(instant))
}