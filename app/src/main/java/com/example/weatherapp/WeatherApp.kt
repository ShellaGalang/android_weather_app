package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.components.WeatherTabs
import com.example.weatherapp.ui.current.CurrentWeatherScreen
import com.example.weatherapp.ui.current.CurrentWeatherViewModel
import com.example.weatherapp.ui.history.WeatherHistoryScreen
import com.example.weatherapp.ui.history.WeatherHistoryViewModel
import com.example.weatherapp.ui.theme.WeatherAppColors
import kotlinx.coroutines.launch

// Control Tab flow
@Composable
fun WeatherApp(
    locationProvider: LocationProvider,
    repository: WeatherRepository,
    modifier: Modifier = Modifier
) {
    
    val pagerState = rememberPagerState { 2 } // Page Count
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WeatherAppColors.backgroundGradient)
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                Box(
                    modifier = Modifier.statusBarsPadding() // Handles device time area
                ) {
                    WeatherTabs(
                        selectedTabIndex = pagerState.currentPage,
                        onTabSelected = { index ->
                            scope.launch {
                                pagerState.animateScrollToPage(index) // Lambda Function SelectedTabIndex
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->

            // Content Area
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) { page ->
                when (page) {
                    0 -> {
                        CurrentWeatherScreen(
                            viewModel = viewModel(
                                factory =
                                    CurrentWeatherViewModel.provideFactory(
                                        repository = repository,
                                        locationProvider = locationProvider
                                    )
                            )
                        )
                    }
                    1 -> {
                        WeatherHistoryScreen(
                            viewModel = viewModel(
                                factory = WeatherHistoryViewModel.provideFactory(repository = repository)
                            )
                        )

                    }
                }
            }
        }
    }

}