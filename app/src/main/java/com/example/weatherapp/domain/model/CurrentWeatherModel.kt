package com.example.weatherapp.domain.model

import com.example.weatherapp.utils.WeatherCondition

// UI model structure all a string
data class CurrentWeatherModel(
    val city: String,
    val country: String,
    val weatherDescription: String,
    val windSpeed: String,
    val windGust: String,
    val humidity: String,
    val temperatureCelsius: String,
    val sunrise: String,
    val sunset: String,
    val currentDate: String, // From API(dt). Formatted, pattern = EEEE, dd MMMM
    val currentTime: String, // From API(ft), Formatted, pattern = h:mm a
    val weatherIcon: WeatherCondition,
    val mainDescription: String,
    val isNightTime: Boolean = false, // Use for changing of sun to moon icon if it is 6PM and weather condition is clear
    val isFromCache: Boolean = false, // For no location or no internet scenario
    val errorMessage: String = "" // ErrorMessage if data is from cache(room db)
)
