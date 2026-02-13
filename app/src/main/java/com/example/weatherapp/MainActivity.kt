package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weatherapp.data.db.WeatherDatabase
import com.example.weatherapp.data.location.FusedLocationProviderImpl
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {

            val weatherDao = WeatherDatabase.getInstance(applicationContext).weatherHistoryDao()
            val locationProvider = FusedLocationProviderImpl(applicationContext)
            val repository = WeatherRepositoryImpl(RetrofitInstance.apiService, weatherDao)

            WeatherAppTheme {
                WeatherApp(
                    locationProvider = locationProvider,
                    repository = repository
                )
            }
        }
    }
}

