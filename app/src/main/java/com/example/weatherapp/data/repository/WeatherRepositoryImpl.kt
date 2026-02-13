package com.example.weatherapp.data.repository


import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.db.WeatherHistoryDao
import com.example.weatherapp.data.db.WeatherHistoryEntity
import com.example.weatherapp.domain.mapper.mapWeatherDescriptionIcon
import com.example.weatherapp.data.remote.WeatherAPIService
import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import com.example.weatherapp.utils.capitalizedWords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

// Two data sources open weather api and room db
/*
Features:
- getCurrentWeather() | Source: API
- addWeatherItem() | Destination: Room DB
- getWeatherHistory() | Source: Room DB
- getLastSavedWeather() | Source: Room DB
*/
class WeatherRepositoryImpl(
    private val apiService: WeatherAPIService,
    private val weatherDao: WeatherHistoryDao
): WeatherRepository {

    private val apiKey = BuildConfig.API_KEY

    // Fetch current weather from Open Weather API by latitude and longitude
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Result<WeatherResponseDto> =

        safeAPICall {
            // Calling of API function to get current weather
           apiService.getCurrentWeatherData(
                latitude = lat,
                longitude = lon,
                apiKey = apiKey // Stored in local.properties BuildConfig.API_KEY
            )
        }

    // Fetch current weather from Open Weather API by city
    override suspend fun getCurrentWeatherByCity(
        city: String
    ): Result<WeatherResponseDto> =
        safeAPICall {
            apiService.getCurrentWeatherByCity(
                city = city,
                apiKey = apiKey
            )
        }


    /**
     * Executes an API call safely, wrapping with try/catch and returning a Result.
     *
     *  Responsibilities:
     *    - Handles exceptions thrown by the API call
     *    - Saves successful responses to the local Room database
     *    - Returns a Result<WeatherResponseDto> with success or failure
     *
     *  Usage:
     *   safeApiCall { apiService.getCurrentWeatherData(lat, lon, apiKey) }
     *   safeApiCall { apiService.getCurrentWeatherByCity(city, apiKey) }
     *
     *   @param apiCall A suspend lambda representing the API endpoint to call
     *   @return Result<WeatherResponseDto> containing either the successful response or the exception
     */
    private suspend fun safeAPICall(
        // Suspend Function. Takes no parameters and returns WeatherResponseDto.
        // Pass any API Call that matches this shape e.g getCurrentWeatherData and getCurrentWeatherByCity
        apiCall: suspend () -> WeatherResponseDto
    ) : Result<WeatherResponseDto> {
        return try {
            // Call passed function
            val response = apiCall()

            // Save weather to DB
            addWeatherItem(response)

            // Set success result
            Result.success(response)
        } catch (e: Exception) {
            // Set error result
            Result.failure(e)
        }
    }


    // Function for adding weather item to Room
    override suspend fun addWeatherItem(responseDto: WeatherResponseDto) {
        // Set weather condition for determining icon to use. Default clear if null
        val weatherIcon = responseDto.weatherList.firstOrNull()?.mainCondition ?: "Clear"
        val weatherDescription = responseDto.weatherList.firstOrNull()?.description ?: ""

        // Map responseDto(API Response) using WeatherHistoryEntity(DB Table Schema)
        val entity = WeatherHistoryEntity(
            cityName = responseDto.cityName,
            country = responseDto.sys.country,
            weatherCondition = weatherDescription.capitalizedWords(),
            windSpeed = responseDto.windDetails.windSpeed,
            windGust = responseDto.windDetails.windGust,
            humidity = responseDto.main.humidity,
            temperature = responseDto.main.temperature,
            sunrise = responseDto.sys.sunrise,
            sunset = responseDto.sys.sunset,
            timestamp = System.currentTimeMillis() / 1000, // Use current Unix time (seconds)
            timeZoneOffset = responseDto.timezoneOffset,
            mainDescription = weatherIcon, // Default clear if null
            weatherIcon = mapWeatherDescriptionIcon(weatherIcon) // Call mapper function to set the icon for the weather condition
        )

        // Calling of function that will insert the entity to the room db
        weatherDao.insertWeather(entity)
    }

    // Fetch last five weather history. Return will be a list of weather history
    override fun getWeatherHistory(): Flow<List<WeatherHistoryEntity>> {
        // Calling of database query function that will get the last five weather history
        return weatherDao.getWeatherLastFive()
    }

    // Fetch last saved weather details
    override suspend fun getLastSavedWeather(): WeatherHistoryEntity? {
        // Call db query that will get the last five weather history but only get the most recent one if there's any
        // .first() takes the first list emitted by the Flow and then closes the connection
        return weatherDao.getWeatherLastFive().first().firstOrNull()
    }
}