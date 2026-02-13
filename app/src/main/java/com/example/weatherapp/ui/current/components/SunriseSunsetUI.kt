package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppColors

@Composable
fun SunriseSunsetUI(
    sunrise: String,
    sunset: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Sunrise
        Image(
            painter = painterResource(R.drawable.ic_sunrise),
            contentDescription = stringResource(R.string.img_sunrise_label),
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = sunrise,
            style = MaterialTheme.typography.titleMedium,
            color = WeatherAppColors.secondaryText
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Sunset
        Image(
            painter = painterResource(R.drawable.ic_sunset),
            contentDescription = stringResource(R.string.img_sunset_label),
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = sunset,
            style = MaterialTheme.typography.titleMedium,
            color = WeatherAppColors.secondaryText
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SunriseSunsetUIPreview() {
    SunriseSunsetUI(
        sunrise = WeatherPreviewData.sample.sunrise,
        sunset = WeatherPreviewData.sample.sunset
    )
}