package com.example.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val tabs = listOf("Current", "History")

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp)
                    .background(
                        Color(0xFF4DB6AC),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        },
        divider = {} // remove bottom divider
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTabIndex, // If index is the same as selectedTabIndex it is selected
                onClick = { onTabSelected(index) }, // Update selectedTabIndex(from WeatherApp) with the index of this tab item
                text = {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index)
                            Color(0xFF00796B)
                        else
                            Color.DarkGray
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherTabsPreview() {
    WeatherTabs(
        0,
        { 0 }
    )
}