package com.example.weatherapp.ui.history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.model.WeatherHistoryItem
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.components.getWeatherIcon
import com.example.weatherapp.ui.theme.WeatherAppColors
import com.example.weatherapp.utils.WeatherCondition

@Composable
fun WeatherHistoryTable(
    historyItems: List<WeatherHistoryItem>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp), // Space around the whole list
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between each glass card
    ) {
        items(historyItems) { item ->
            WeatherHistoryRow(
                item = item,
                condition = item.weatherIcon,
                isNightTime = item.isNightTime
            )
        }
    }
}

@Composable
fun WeatherHistoryRow(
    item: WeatherHistoryItem,
    condition: WeatherCondition,
    isNightTime: Boolean
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, RoundedCornerShape(20.dp))
            .background(
                color = Color(0xFF6ED1D6),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // City, Country, Date
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.cityName,
                    style = MaterialTheme.typography.titleMedium,
                    color = WeatherAppColors.primaryText

                )
                Text(
                    text = item.country,
                    style = MaterialTheme.typography.bodySmall,
                    color = WeatherAppColors.secondaryText
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = WeatherAppColors.secondaryText
                )
            }

            Column(
                modifier = Modifier.width(100.dp), // FIXED WIDTH - uniform cards
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Weather Condition Icon
                Image(
                    painter = painterResource(id = getWeatherIcon(condition, isNightTime)),
                    contentDescription = condition.name,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Weather Condition Text
                Text(
                    text = item.weatherCondition,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    lineHeight = 14.sp,
                    color = WeatherAppColors.primaryText
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Temperature in celsius
                Text(
                    text = item.temperature,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = WeatherAppColors.primaryText
                )

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun WeatherHistoryTablePreview() {
    WeatherHistoryTable(WeatherHistoryPreviewData.history)
}