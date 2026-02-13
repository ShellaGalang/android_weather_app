package com.example.weatherapp.ui.current

import com.example.weatherapp.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import com.example.weatherapp.domain.model.CurrentWeatherModel


@Composable
fun CurrentWeatherScreenNoDesign(
    viewModel: CurrentWeatherViewModel,
    modifier: Modifier = Modifier
) {

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is CurrentWeatherUiState.Loading -> {
            CircularProgressIndicator()
        }

        is CurrentWeatherUiState.Success -> {
            CurrentWeatherData(uiState.weatherDetails)
        }

        is CurrentWeatherUiState.Empty -> {
            Text(uiState.message)
        }

        is CurrentWeatherUiState.Error -> {
            Text(uiState.message)

        }
    }
}

@Composable
fun CurrentWeatherData(
    weather: CurrentWeatherModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City Name
        Text(
            text = weather.city,
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(5.dp))
        // Country
        Text(
            text = weather.country,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        // Icon, Temp and Weather Description
        WeatherDescription(
            icon = R.drawable.ic_weather_sunny,
            description = weather.weatherDescription,
            temperatureCelsius = weather.temperatureCelsius
        )
        Spacer(modifier = Modifier.height(30.dp))
        //Sunrise and Sunset
        SunriseAndSunset(weather.sunrise, weather.sunset)
        Spacer(modifier = Modifier.height(30.dp))
        // Date
        Text(
            text = weather.currentDate,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(30.dp))
        // Other Details Card
        WeatherDetailsRow(
            humidity = weather.humidity,
            windSpeed = weather.windSpeed,
            windGust = weather.windGust
        )

    }
}

@Composable
fun WeatherDescription(
    @DrawableRes icon: Int,
    description: String,
    temperatureCelsius: String,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null, // For now
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = temperatureCelsius,
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = description,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

// Sunrise and Sunset
@Composable
fun SunriseAndSunset(
    sunrise: String,
    sunset: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        // Sunrise
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = sunrise,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )

        // Sunset
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = sunset,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// Calling of Card Info
@Composable
fun WeatherDetailsRow(
    humidity: String,
    windSpeed: String,
    windGust: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CardInfo(
            label = stringResource(R.string.humidity_label),
            value = humidity,
            icon = Icons.Default.WaterDrop,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(5.dp))
        CardInfo(
            label = stringResource(R.string.wind_speed_label),
            value = windSpeed,
            icon = Icons.Default.Air,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(5.dp))
        CardInfo(
            label = stringResource(R.string.wind_gust_label),
            value = windGust,
            icon = Icons.Default.WindPower,
            modifier = Modifier.weight(1f)
        )
    }
}

// 1 Card Design
@Composable
fun CardInfo(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Blue
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue
            )
        }

    }
}




