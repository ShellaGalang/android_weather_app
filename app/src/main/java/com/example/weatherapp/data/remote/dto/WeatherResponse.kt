package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Kotlin representation (mapping) of the JSON response.
data class WeatherResponseDto (
    @SerializedName("name") val cityName: String, // City Name
    @SerializedName("sys") val sys : SysDto, // Country, Sunrise, Sunset
    @SerializedName("main") val main: MainDto, // Temp
    @SerializedName("weather") val weatherList: List<WeatherDescriptionDto>, // Icon
    @SerializedName("wind") val windDetails: WindDto,
    @SerializedName("dt") val timestamp: Long, // Current Time
    @SerializedName("timezone") val timezoneOffset: Int
)

data class SysDto(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)

data class MainDto(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("humidity") val humidity: Double
)

data class WeatherDescriptionDto(
    @SerializedName("main") val mainCondition: String, // e.g., "Clear"
    @SerializedName("description") val description: String,
    @SerializedName("icon") val iconCode: String,
)

data class WindDto(
    @SerializedName("speed") val windSpeed: Double,
    @SerializedName("gust") val windGust: Double
)