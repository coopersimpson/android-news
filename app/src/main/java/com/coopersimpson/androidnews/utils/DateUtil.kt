package com.coopersimpson.androidnews.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertDate(input: String): String {
    val apiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())  // Incoming UTC format
    val desiredFormat = DateTimeFormatter.ofPattern("MMM d, h:mm a", Locale.getDefault()) // Output in local format

    val utcDateTime = LocalDateTime.parse(input, apiFormat)
        .plusHours(12); // Add 12 hrs cause API is 12 hours behind real UTC time ?
    val zonedUtc = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC"));

    val localZoned = zonedUtc.withZoneSameInstant(ZoneId.systemDefault())

    return desiredFormat.format(localZoned)
}