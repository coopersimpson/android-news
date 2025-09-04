package com.coopersimpson.androidnews.utils

import java.text.SimpleDateFormat

fun convertDate(input: String): String {
    val apiFormat = "yyyy-MM-dd HH:mm:ss"
    val desiredFormat = "EEE, MMM d, h:mm a"

    val parser = SimpleDateFormat(apiFormat)
    val printer = SimpleDateFormat(desiredFormat)

    val parsed = parser.parse(input) ?: return input

    return printer.format(parsed)
}