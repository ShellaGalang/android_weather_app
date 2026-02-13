package com.example.weatherapp.ui.current


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.ui.components.ErrorUI
import com.example.weatherapp.ui.current.components.CityCountryUI
import com.example.weatherapp.ui.current.components.DateUI
import com.example.weatherapp.ui.current.components.OtherWeatherStatsUI
import com.example.weatherapp.ui.current.components.SearchBarUI
import com.example.weatherapp.ui.current.components.SunriseSunsetUI
import com.example.weatherapp.ui.current.components.WeatherConditionUI
import com.example.weatherapp.ui.theme.WeatherAppTheme


@Composable
fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel
) {
    // State to hold city input from search bar
    var city by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        /**
         * Pass state and events to the stateless search bar
         */
        SearchBarUI(
            city = city,
            onChange = { city = it },
            onSearchClick = { viewModel.loadWeather(city) } // On click of search button pass a function that will call loadWeather() from view model
        )

        Spacer(modifier = Modifier.height(16.dp))

        // This box will occupy the remaining space (For scrolling purposes)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState), // Apply scroll here
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centers content if it's smaller than the screen

            ) {
                when (val state = uiState) {
                    is CurrentWeatherUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is CurrentWeatherUiState.Success -> {
                        // If data is from DB show toast message for the error message
                        if (state.weatherDetails.isFromCache) {
                            Toast.makeText(
                                context,
                                state.weatherDetails.errorMessage,
                                Toast.LENGTH_LONG).show()
                        }
                        // weatherDetails is of type CurrentWeatherModel(Data class UI structure)
                        CurrentWeatherUIStructure(state.weatherDetails)
                    }

                    is CurrentWeatherUiState.Empty -> {
                        ErrorUI(state.message)
                    }

                    is CurrentWeatherUiState.Error -> {
                        ErrorUI(state.message)

                    }
                }
            }
        }
    }
}


@Composable
fun CurrentWeatherUIStructure(
    weather: CurrentWeatherModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City/Country UI
        CityCountryUI(weather.city, weather.country)
        Spacer(modifier = Modifier.height(20.dp))
        //WeatherCondition UI
        WeatherConditionUI(
            condition = weather.weatherIcon, // Get from uiState in viewmodel
            isNightTime = weather.isNightTime,
            temp = weather.temperatureCelsius,
            weather.weatherDescription
        )
        Spacer(modifier = Modifier.height(10.dp))
        SunriseSunsetUI(weather.sunrise, weather.sunset)
        Spacer(modifier = Modifier.height(20.dp))
        DateUI(weather.currentDate, weather.currentTime) // Current time of City not the device
        Spacer(modifier = Modifier.height(20.dp))
        OtherWeatherStatsUI(
            weather.humidity,
            weather.windSpeed,
            weather.windGust
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CurrentWeatherUiStructurePreview() {
    WeatherAppTheme {
        Surface(color = Color.DarkGray) {
            CurrentWeatherUIStructure(weather = WeatherPreviewData.sample)
        }

    }

}
