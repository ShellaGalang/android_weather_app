package com.example.weatherapp.domain.model

import com.example.weatherapp.utils.WeatherCondition

// UI model structure all a string
data class WeatherHistoryItem(
    val cityName: String,
    val country: String,
    val weatherCondition: String,
    val windSpeed: String,
    val windGust: String,
    val humidity: String,
    val temperature: String,
    val sunrise: String,
    val sunset: String,
    val date: String,
    val weatherIcon: WeatherCondition,
    val isNightTime: Boolean = false
)
