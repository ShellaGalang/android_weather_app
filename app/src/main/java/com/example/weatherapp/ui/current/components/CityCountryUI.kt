package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.SoftWhite

@Composable
fun CityCountryUI(
    city: String,
    country: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City
        Text(
            text = city,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = SoftWhite
        )
        // Country
        Text(
            text = country,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light,
            color = SoftWhite
        )

    }
}

@Preview(showBackground = false)
@Composable
fun CityCountryUIPreview() {
    CityCountryUI(
        WeatherPreviewData.sample.city,
        WeatherPreviewData.sample.country
    )
}