package com.example.weatherapp.ui.current

import com.example.weatherapp.domain.model.CurrentWeatherModel

sealed interface CurrentWeatherUiState {

    object Loading : CurrentWeatherUiState

    data class Success(
        val weatherDetails: CurrentWeatherModel
    ) : CurrentWeatherUiState

    // State for no internet/no location permission and there's no data on db
    data class Empty(
        val message: String
    ): CurrentWeatherUiState

    data class Error(
        val message: String
    ) : CurrentWeatherUiState
}

