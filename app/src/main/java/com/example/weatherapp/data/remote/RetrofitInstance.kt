package com.example.weatherapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// The retrofit provider

object RetrofitInstance {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val apiService: WeatherAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPIService::class.java)
    }
}