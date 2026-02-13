package com.example.weatherapp.ui.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.SoftWhite
import com.example.weatherapp.utils.formatDate

@Composable
fun ScreenTitleUI(
    // date: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Screen Title
        Text(
            text = stringResource(R.string.history_title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(5.dp))

        // As of datetime
        val currentDatetimeMs = System.currentTimeMillis() / 1000 // Get current datetime in ms

        Text(
            text = stringResource(R.string.as_of_label) + " " +
                    formatDate(currentDatetimeMs,  "MMMM dd, yyyy, h:mm a"),
            color = SoftWhite,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Preview(showBackground = false
)
@Composable
fun WeatherHistoryTitleUI() {
    ScreenTitleUI()
}