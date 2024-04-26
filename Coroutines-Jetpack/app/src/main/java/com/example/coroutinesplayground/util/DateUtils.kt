package com.example.coroutinesplayground.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.util.Date

fun convertTimestampToStringDate(timestamp: String): String = try {
    val timestampLong = timestamp.toLong() * 1000 // Assuming the timestamp is in seconds
    val date = Date(timestampLong)
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.format(date)
} catch (e: Exception) {
    e.printStackTrace()
    "Error parsing timestamp"
}

fun extractMonthFromTimestamp(timestamp: String): String {
    val instant = Instant.ofEpochSecond(timestamp.toLong())
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Extract the month value (1 to 12)
    return Month.of(localDateTime.month.value).toString()
}