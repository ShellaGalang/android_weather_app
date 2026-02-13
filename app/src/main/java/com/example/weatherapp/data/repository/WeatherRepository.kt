package com.example.weatherapp.data.repository

import com.example.weatherapp.data.db.WeatherHistoryEntity
import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import kotlinx.coroutines.flow.Flow

// Interface: What app can do without worrying what it does
interface WeatherRepository {

    // Get current weather by device's latitude and longitude
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Result<WeatherResponseDto>
    
    // Get current weather by city name
    suspend fun getCurrentWeatherByCity(city: String) : Result<WeatherResponseDto>

    fun getWeatherHistory(): Flow<List<WeatherHistoryEntity>>

    suspend fun addWeatherItem(responseDto: WeatherResponseDto)

    // For no internet/no location permission. Can return no data
    suspend fun getLastSavedWeather(): WeatherHistoryEntity?
}
