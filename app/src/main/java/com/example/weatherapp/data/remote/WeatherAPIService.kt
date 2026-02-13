package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

// Interface to define the API calls
interface WeatherAPIService {

    // Get Current Weather by latitude & longitude
    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric" // Ensure that temperature is a celsius
    ): WeatherResponseDto

    // Get current weather by city name
    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric" // Ensure that temperature is a celsius
    ): WeatherResponseDto
}