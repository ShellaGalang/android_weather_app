package com.example.weatherapp.ui.current


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.data.location.LocationResult
import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.mapper.mapWeatherDescriptionIcon
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.utils.capitalizedWords
import com.example.weatherapp.utils.isNighttime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import java.time.ZoneOffset
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Middle man of repository and UI
class CurrentWeatherViewModel(
    private val repository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {


    // Mutable state flow. Initial value of loading
    private val _uiState = MutableStateFlow<CurrentWeatherUiState>(
        CurrentWeatherUiState.Loading)

    // Observable state
    val uiState: StateFlow<CurrentWeatherUiState> = _uiState


    init {
        loadWeather(city = "") // Calling of get Weather included
    }

    /**
     * This function loads weather either by City or using device's latitude and longitude
     *
     * Can be called by the UI
     *
     * @param city String. "" = value in init.
     *
     * Usage:
     * loadWeather(city = "Manila")
     */
    fun loadWeather(city: String) {
        viewModelScope.launch {
            if (city.isNotBlank()) {
                loadCurrentWeatherByCity(city)
            } else {
                fetchLocation()
            }
        }
    }

    // Function to get Device's Location
    suspend fun fetchLocation() {
        // When result is Success(data class), Permission Denied(object), or Unavailable(object). Use LocationResult(sealed class)
        when (val result = locationProvider.getCurrentLocation()) {
            is LocationResult.Success -> {

                // Location fetched successfully. Call getCurrentWeather
                loadCurrentWeatherByLatLon(result.lat, result.lon)
            }
            is LocationResult.PermissionDenied -> {
                // Permission Denied used default city to display
                loadCurrentWeatherByCity("Malolos")
            }
            is LocationResult.LocationUnavailable -> {
                // Call function that will check if there are available weather history to display that instead
                loadFromCacheOrEmpty("Location Unavailable")
            }
        }
    }

    // Helper Function: Get Current Weather by lat and lon
    private suspend fun loadCurrentWeatherByLatLon(lat: Double, lon: Double) {
        _uiState.value = CurrentWeatherUiState.Loading

        // Call Helper Function and pass the api call to use
        callWeatherAPIEndPoint {
            repository.getCurrentWeather(lat, lon)
        }

    }

    // Helper Function: Get Current Weather by City
    private suspend fun loadCurrentWeatherByCity(city: String) {
        _uiState.value = CurrentWeatherUiState.Loading

        // Call Helper Function and pass the api call to use
        callWeatherAPIEndPoint {
            repository.getCurrentWeatherByCity(city)
        }

    }

    // Helper Function: Handler for no internet or no Location Permission.
    // Param: errorMessage. This will be used if there's no weather history fetched
    private suspend fun loadFromCacheOrEmpty(errorMessage: String) {
        // Get last save weather data. From room db
        val lastSaved = repository.getLastSavedWeather()

        // If lastSaved is not null, set CurrentWeatherModel(UI Structure Model) and use that to display in the UI instead
        if (lastSaved != null) {
            // Set CurrentWeatherModel from the lastSaved.
            // Source of this one is DB so the date or time is not yet formatted
            val cachedValues = CurrentWeatherModel(
                city = lastSaved.cityName,
                country = lastSaved.country,
                weatherDescription = lastSaved.weatherCondition,
                windSpeed = lastSaved.windSpeed.toString(),
                windGust = lastSaved.windGust.toString(),
                humidity = lastSaved.humidity.toString(),
                temperatureCelsius = "${lastSaved.temperature.toInt()}°C",
                sunrise = formatLongTime(
                    lastSaved.sunrise,
                    lastSaved.timeZoneOffset,
                    "h:mm a"
                ),
                sunset = formatLongTime(
                    lastSaved.sunset,
                    lastSaved.timeZoneOffset,
                    "h:mm a"
                ),
                currentDate = formatLongTime(
                    lastSaved.timestamp,
                    lastSaved.timeZoneOffset,
                    "EEEE, dd MMMM"
                ),
                currentTime = formatLongTime(
                    lastSaved.timestamp,
                    lastSaved.timeZoneOffset,
                    "h:mm a"),
                mainDescription = lastSaved.mainDescription,
                weatherIcon = lastSaved.weatherIcon,
                isNightTime = isNighttime(lastSaved.timestamp, lastSaved.timeZoneOffset), // Call helper function to determine if it is night time
                isFromCache = true,
                errorMessage = "${errorMessage}. Loading data from DB..."
            )

            // Set Success State data from room db
            _uiState.value = CurrentWeatherUiState.Success(cachedValues)
        } else {
            // No weather history. Set error to display instead
            _uiState.value = CurrentWeatherUiState.Empty(errorMessage)
        }
    }

    /**
     * Safely calls a weather API endpoint and updates the UI state.
     *
     * @param apiFunction a suspend function that returns Result<WeatherResponseDto>
     *     Example: repository.getCurrentWeather(lat, lon) or repository.getCurrentWeatherByCity
     *
     * Usage:
     * callWeatherAPIEndPoint { repository.getCurrentWeather(lat, lon) }
     */
    private suspend fun callWeatherAPIEndPoint(
        apiFunction: suspend () -> Result<WeatherResponseDto>
    ) {
        val apiResponse = apiFunction()

        apiResponse
            // Successful API fetch
            .onSuccess { response ->

                // Get country's full name
                val countryName = getCountryName(
                    response.sys.country
                ).ifBlank { response.sys.country }

                // Set weather condition for determining icon to use
                val weatherIcon = response.weatherList.firstOrNull()?.mainCondition ?: "Clear"
                val weatherDescription = response.weatherList.firstOrNull()?.description ?: ""

                // Map API model structure(responseDto) to UI model structure(CurrentWeatherModel)
                val weatherDetails = CurrentWeatherModel(
                    city = response.cityName,
                    country = countryName,
                    weatherDescription = weatherDescription.capitalizedWords(),
                    windSpeed = response.windDetails.windSpeed.toString(),
                    windGust = response.windDetails.windGust.toString(),
                    humidity = response.main.humidity.toString(),
                    temperatureCelsius = "${response.main.temperature.toInt()}°C",
                    sunrise = formatLongTime(
                        response.sys.sunrise,
                        response.timezoneOffset,
                        "h:mm a"
                    ),
                    sunset = formatLongTime(
                        response.sys.sunset,
                        response.timezoneOffset,
                        "h:mm a"
                    ),
                    currentDate = formatLongTime(
                        response.timestamp,
                        response.timezoneOffset,
                        "EEEE, dd MMMM"
                    ),
                    currentTime = formatLongTime(
                        response.timestamp,
                        response.timezoneOffset,
                        "h:mm a"
                    ),
                    mainDescription = weatherIcon,
                    isNightTime = isNighttime(response.timestamp,response.timezoneOffset), // Call helper function to determine if it is night time
                    weatherIcon = mapWeatherDescriptionIcon(weatherIcon)
                )

                // Set success Ui State. weatherDetails is type CurrentWeatherModel
                _uiState.value = CurrentWeatherUiState.Success(weatherDetails)
            }
            .onFailure { it ->
                // Call function that will check if there are available weather history to display that instead
                loadFromCacheOrEmpty(
                    it.message?.takeIf { it.isNotBlank() } ?:
                    "Failed to fetch current weather data"
                )
            }
    }

    /**
     * Helper function: Set isFromCache to false after showing the Toast Message. Prevents showing the
     * toast message again, every time the CurrentWeatherScreen is "re-entered"
     *
     * Usages: In UI - viewModel.errorShown
     */
    fun errorShown() {
        // Set currentState
        val currentState = _uiState.value

        // If currentState is sucess
        if (currentState is CurrentWeatherUiState.Success) {
            // Create a new version of the state where isFromCache is now false
            // or the error message is empty
            _uiState.value = currentState.copy(
                weatherDetails = currentState.weatherDetails.copy(isFromCache = false)
            )
        }
    }

    // Internal factory
    companion object{
        fun provideFactory(
            repository: WeatherRepository,
            locationProvider: LocationProvider
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>) : T {
                    return CurrentWeatherViewModel(repository, locationProvider) as T
                }
            }
    }

}

// Format long timestamp to function
private fun formatLongTime(
    unixTime: Long,
    timeZoneOffset: Int,
    pattern: String
): String {
   // Create an Instant from the UTC timestamp
    val instant = Instant.ofEpochSecond(unixTime)

    // Create ZoneOffset from the seconds provided by the API
    val zoneOffset = ZoneOffset.ofTotalSeconds(timeZoneOffset)

    // 4. Format for display (e.g., 6:12 AM)
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())

    return LocalDateTime.ofInstant(instant, zoneOffset).format(formatter)
}

// Helper function: Get country's full name
private fun getCountryName(countryCode: String): String {

    // Create a locale with just the region using forLanguageTag
    val locale = Locale.forLanguageTag("und-$countryCode")

    return locale.getDisplayCountry(Locale.ENGLISH) // "Philippines"
}

