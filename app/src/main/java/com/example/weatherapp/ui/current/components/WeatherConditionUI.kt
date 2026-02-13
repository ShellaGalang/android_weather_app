package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.components.getWeatherIcon
import com.example.weatherapp.ui.theme.SoftWhite
import com.example.weatherapp.utils.WeatherCondition

// Weather Condition UI
@Composable
fun WeatherConditionUI(
    condition: WeatherCondition,
    isNightTime: Boolean,
    temp: String,
    weatherDescription: String
) {
    BlobContainer(
        modifier = Modifier.size(350.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherIcon(
                condition = condition,
                isNightTime = isNightTime,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = temp,
                fontSize = 42.sp,
                fontWeight = FontWeight.Light,
                color = SoftWhite
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = weatherDescription,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.05f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                color = Color.White
            )
        }
    }
}



// Display Weather Icon
@Composable
fun WeatherIcon(
    condition: WeatherCondition,
    isNightTime: Boolean,
    modifier: Modifier = Modifier
) {
    Image(
        // Call function to get weather icon
        painter = painterResource(id = getWeatherIcon(condition, isNightTime)),
        contentDescription = condition.name,
        modifier = modifier.size(160.dp),
        contentScale = ContentScale.Fit

    )
}

// Draw blob background and on top of it is the composable of weather condition
@Composable
fun BlobContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_blob_background),
            contentDescription = stringResource(R.string.img_blob_bg_label),
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit
        )

        // Foreground content
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherConditionUIPreview() {
    WeatherConditionUI(
        condition = WeatherConditionPreviewData.condition,
        temp = WeatherPreviewData.sample.temperatureCelsius,
        isNightTime = true,
        weatherDescription = WeatherPreviewData.sample.weatherDescription
    )
}

