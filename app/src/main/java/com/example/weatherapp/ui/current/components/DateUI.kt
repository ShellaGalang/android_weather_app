package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.WeatherAppColors

@Composable
fun DateUI(
    date: String,
    time: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
            color = WeatherAppColors.secondaryText
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Date
        Text(
            text = time,
            style = MaterialTheme.typography.bodyLarge,
            color = WeatherAppColors.secondaryText
        )
    }
}

@Preview
@Composable
fun DateUIPreview(){
    DateUI(
        WeatherPreviewData.sample.currentDate,
        WeatherPreviewData.sample.currentTime
    )
}