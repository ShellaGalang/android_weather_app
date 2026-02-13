package com.example.weatherapp.data.location

sealed class LocationResult {
    data class Success (val lat: Double, val lon: Double) : LocationResult()
    object PermissionDenied : LocationResult()
    object  LocationUnavailable: LocationResult()
}