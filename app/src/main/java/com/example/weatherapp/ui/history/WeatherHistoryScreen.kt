package com.example.weatherapp.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.domain.model.WeatherHistoryItem
import com.example.weatherapp.ui.components.ErrorUI
import com.example.weatherapp.ui.history.components.ScreenTitleUI
import com.example.weatherapp.ui.history.components.WeatherHistoryTable
import com.example.weatherapp.ui.theme.WeatherAppTheme


@Composable
fun WeatherHistoryScreen(
    viewModel: WeatherHistoryViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is WeatherHistoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is WeatherHistoryUiState.Success -> {
                WeatherHistoryUIStructure(state.history)
            }

            is WeatherHistoryUiState.Empty -> {
                ScreenTitleUI()
                ErrorUI(state.message)
            }

            is WeatherHistoryUiState.Error -> {
                ErrorUI(state.message)
            }
        }
    }
}



@Composable
fun WeatherHistoryUIStructure(
    historyItems: List<WeatherHistoryItem>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Get current date time
        ScreenTitleUI()
        Spacer(modifier = Modifier.height(20.dp))
        WeatherHistoryTable(historyItems)

    }
}


@Preview
@Composable
fun WeatherHistoryContainerPreview() {
    WeatherAppTheme{
        Surface(color = Color.DarkGray) {
            WeatherHistoryUIStructure(WeatherHistoryPreviewData.history)
        }
    }

}