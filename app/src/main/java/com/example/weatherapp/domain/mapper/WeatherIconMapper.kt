package com.example.weatherapp.domain.mapper

import com.example.weatherapp.utils.WeatherCondition

fun mapWeatherDescriptionIcon(description: String): WeatherCondition {
    return when(description) {
        "Clear" -> WeatherCondition.CLEAR
        "Clouds" -> WeatherCondition.CLOUDY
        "Rain", "Drizzle", "Thunderstorm" -> WeatherCondition.RAINY
        else -> WeatherCondition.CLOUDY
    }
}