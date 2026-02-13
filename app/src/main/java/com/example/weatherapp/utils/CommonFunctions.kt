package com.example.weatherapp.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

// Format long timestamp to function
fun formatDate(
    unixTime: Long,
    pattern: String
): String {
    // Create an Instant from the UTC timestamp
    val instant = Instant.ofEpochSecond(unixTime)

    // Uses phone local time
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Format for display (e.g., 6:12 AM)
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())

    return dateTime.format(formatter)
}

// Capitalized every first letter of a word
fun String.capitalizedWords(): String =
    lowercase()
        .split(" ")
        .joinToString(" ") {
            it.replaceFirstChar {  char -> char.uppercase() }
        }

/**
 * Helper function: Determines if it is 6 PM or later in the target city
 *
 * @param dt The current UTC Unix timestamp from the API (in seconds)
 * @param timeZoneOffset The timezone shift in seconds from UTC (from API)
 */
fun isNighttime(dt: Long, timeZoneOffset: Int): Boolean {
    // Calculate the local time by adding the offset to the UTC timestamp
    val localSeconds = dt + timeZoneOffset

    // Convert to an Instant and get the hour in UTC
    val hour = Instant.ofEpochSecond(localSeconds)
        .atZone(ZoneOffset.UTC)
        .hour

    return hour >= 18 // Military time equivalent to 6 PM
}

// Current Datetime formatter
object DateTimeFormatter {
    private  val currentDateFormatter =
        DateTimeFormatter.ofPattern(
            "MMM dd, yyyy, hh:mm a",
            Locale.ENGLISH
        )
    fun formatCurrentDateTime(dateTime: LocalDateTime) : String {
        return dateTime.format(currentDateFormatter)
    }
}
