package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.SoftWhite

@Composable
fun OtherWeatherStatsUI(
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
        CardDetails(
            label = stringResource(R.string.humidity_label),
            value = humidity,
            icon = R.drawable.ic_humidity,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(5.dp))
        CardDetails(
            label = stringResource(R.string.wind_speed_label),
            value = windSpeed,
            icon = R.drawable.ic_wind_speed,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(5.dp))
        CardDetails(
            label = stringResource(R.string.wind_gust_label),
            value = windGust,
            icon = R.drawable.ic_wind_gust,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CardDetails(
    label: String,
    value: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6ED1D6)),
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = (RoundedCornerShape(10.dp))
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.img_other_details_label),
                Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = SoftWhite
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = SoftWhite
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OtherWeatherStatsUIPreview() {
    OtherWeatherStatsUI(
        WeatherPreviewData.sample.humidity,
        WeatherPreviewData.sample.windSpeed,
        WeatherPreviewData.sample.windGust
    )
}