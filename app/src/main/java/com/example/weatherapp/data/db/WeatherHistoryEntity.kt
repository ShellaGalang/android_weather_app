package com.example.weatherapp.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.utils.WeatherCondition

// Database table schema
@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val country: String,
    val weatherCondition: String,
    val windSpeed: Double,
    val windGust: Double,
    val humidity: Double,
    val temperature: Double,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long,
    val timeZoneOffset: Int,
    val weatherIcon: WeatherCondition,
    val mainDescription: String // Rainy, Clouds, Clear
)