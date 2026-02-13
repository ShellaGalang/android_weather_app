package com.example.weatherapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.WeatherHistoryItem
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.isNighttime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherHistoryViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    // Mutable state flow. Initial value of loading
    private val _uiState = MutableStateFlow<WeatherHistoryUiState>(WeatherHistoryUiState.Loading)
    // Observable state. Exposed
    val uiState: StateFlow<WeatherHistoryUiState> = _uiState

    init {
        loadWeatherHistory()
    }

    private fun loadWeatherHistory() {
        viewModelScope.launch {
            // Initial loading state
            _uiState.value = WeatherHistoryUiState.Loading

            // Using .collect since output of getWeatherHistory is a flow
            repository.getWeatherHistory()
                .collect { entities ->
                    // This blocks runs EVERY time the DB changes
                    try {
                        if (entities.isEmpty()) {
                            _uiState.value = WeatherHistoryUiState.Empty("No weather history found")
                        } else {
                            // Map DB model structure(entity) to UI model structure(Weather History Item) for displaying in UI
                            val historyItems = entities.map { entity ->
                                WeatherHistoryItem(
                                    cityName = entity.cityName,
                                    country = entity.country,
                                    weatherCondition = entity.weatherCondition,
                                    windSpeed = entity.windSpeed.toString(),
                                    windGust = entity.windGust.toString(),
                                    humidity = entity.humidity.toString(),
                                    temperature = "${entity.temperature.toInt()}°C",
                                    sunrise = formatDate(entity.sunrise, "h:mm a"),
                                    sunset = formatDate(entity.sunset, "h:mm a"),
                                    date = formatDate(entity.timestamp, "dd MMM, h:mm a"),
                                    weatherIcon = entity.weatherIcon,
                                    isNightTime = isNighttime(entity.timestamp, entity.timeZoneOffset)
                                )
                            }
                            // Update the state with the new list
                            _uiState.value = WeatherHistoryUiState.Success(historyItems)
                        }
                    } catch (e: Exception) {
                        _uiState.value = WeatherHistoryUiState.Error(
                            // .takeIf { it.isNotBlank() } ?: = if the string is "" or " ", it turns it into null. Since it is now null it falls back to default message
                            e.message?.takeIf { it.isNotBlank() } ?: "Failed to load weather history"
                        )
                    }
                }
        }
    }

    // Internal factory
    companion object{
        fun provideFactory(
            repository: WeatherRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>) : T {
                    return WeatherHistoryViewModel(repository) as T
                }
            }
    }
}