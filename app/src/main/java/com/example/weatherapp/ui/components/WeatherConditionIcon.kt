package com.example.weatherapp.ui.components

import androidx.annotation.DrawableRes
import com.example.weatherapp.R
import com.example.weatherapp.utils.WeatherCondition

// Get the weather icon to use, depending on the weather
@DrawableRes
fun getWeatherIcon(
    condition: WeatherCondition,
    isNightTime: Boolean,
): Int {
    return when(condition) {
        // If it is 6PM and condition is clear change icon to moon
        WeatherCondition.CLEAR -> {
            if(isNightTime) {
                R.drawable.ic_weather_moon
            } else {
                R.drawable.ic_weather_sunny
            }
        }
        WeatherCondition.CLOUDY -> R.drawable.ic_weather_cloudy
        WeatherCondition.RAINY -> R.drawable.ic_weather_rainy
    }
}
