package com.example.weatherapp.ui.current

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.utils.WeatherCondition

@Composable
fun WeatherScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WeatherCard()
    }
}

@Composable
fun WeatherCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF7FD7DB),
                        Color(0xFFEFEFEF)
                    )
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(24.dp))
        HeroWeather(WeatherCondition.CLEAR, isNightTime = false)
        Spacer(modifier = Modifier.height(20.dp))
        DateText()
        Spacer(modifier = Modifier.height(20.dp))
        WeatherStats()
        Spacer(modifier = Modifier.height(20.dp))
        ForecastList()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
        Text(
            text = "Madrid",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
    }
}

@DrawableRes
fun weatherIconRes(
    condition: WeatherCondition,
    isNightTime: Boolean
): Int {
    return when (condition) {
        // If it is 6PM and condition is clear change icon to moon
        WeatherCondition.CLEAR -> {
            if(isNightTime) {
                R.drawable.ic_weather_moon
            } else {
                R.drawable.ic_weather_sunny
            }
        }
        WeatherCondition.CLOUDY -> R.drawable.ic_weather_cloudy
        WeatherCondition.RAINY -> R.drawable.ic_weather_rainy
    }
}

@Composable
fun WeatherIcon(
    condition: WeatherCondition,
    isNightTime: Boolean,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = weatherIconRes(condition, isNightTime)),
        contentDescription = condition.name,
        modifier = modifier.size(160.dp),
        contentScale = ContentScale.Fit
    )
}



@Composable
fun TemperatureSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "29°",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Sunny",
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun HeroWeather(
    condition: WeatherCondition,
    isNightTime: Boolean
) {
    BlobContainer(
        modifier = Modifier.size(350.dp) // ⬅️ ONE place to control size
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherIcon(
                condition = condition,
                isNightTime = isNightTime,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "29°",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Sunny",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
fun BlobContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Blob background
        Image(
            painter = painterResource(R.drawable.ic_blob_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit
        )

        // Foreground content
        content()
    }
}


/*
@Composable
fun HeroWeather1(
    condition: WeatherCondition,
    temperature: String,
    label: String
) {
    Box(
        modifier = Modifier
            .size(220.dp),
        contentAlignment = Alignment.Center
    ) {

        // 🌫 Blob background image
        Image(
            painter = painterResource(R.drawable.ic_blob_background),
            contentDescription = null,
            modifier = Modifier
                .size(800.dp)
        )

        // ☀️ Foreground content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherIcon(
                condition = condition,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = temperature,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = label,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

 */

@Composable
fun DateText() {
    Text(
        text = "Wednesday, 7 July",
        color = Color.Gray,
        fontSize = 14.sp
    )
}

@Composable
fun WeatherStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.4f))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherStatItem("3 m/s", "Wind")
        WeatherStatItem("40%", "Humidity")
        WeatherStatItem("7:42", "Sunrise")
    }
}

@Composable
fun WeatherStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ForecastList() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ForecastRow("Thursday", "🌤", "22°")
        ForecastRow("Friday", "🌧", "24°")
        ForecastRow("Saturday", "☀️", "27°")
    }
}

@Composable
fun ForecastRow(day: String, icon: String, temp: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(day)
        Text(icon, fontSize = 18.sp)
        Text(temp, fontWeight = FontWeight.Bold)
    }
}