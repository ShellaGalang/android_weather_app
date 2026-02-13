package com.example.weatherapp.ui.history

import com.example.weatherapp.domain.model.WeatherHistoryItem

sealed interface WeatherHistoryUiState {
    object Loading: WeatherHistoryUiState

    data class Success(
        val history: List<WeatherHistoryItem>
    ) : WeatherHistoryUiState

    data class Empty(
        val message: String
    ) : WeatherHistoryUiState

    data class Error(
        val message: String
    ) : WeatherHistoryUiState
}

