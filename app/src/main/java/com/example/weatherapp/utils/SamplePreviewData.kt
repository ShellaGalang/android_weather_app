import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.WeatherHistoryItem
import com.example.weatherapp.utils.WeatherCondition

object WeatherPreviewData {
    val sample = CurrentWeatherModel(
        city = "Malolos",
        country = "Philippines",
        weatherDescription = "Sunny",
        windSpeed = "14.10",
        windGust = "28.20",
        humidity = "34.10",
        temperatureCelsius = "30.0°",
        sunrise = "6:00 AM",
        sunset = "6:00 PM",
        currentDate = "Friday, 6 February",
        mainDescription = "Cloudy",
        weatherIcon = WeatherCondition.CLEAR,
        currentTime = "10:57 AM"
    )
}

object WeatherConditionPreviewData {
     val condition = WeatherCondition.CLEAR
}

// For history screen
object WeatherHistoryPreviewData {
    val history = listOf(
        WeatherHistoryItem(
            cityName = "Malolos",
            country = "Philippines",
            weatherCondition = "Sunny",
            windSpeed = "14.10",
            windGust = "28.20",
            humidity = "34.10",
            temperature = "30.0°",
            sunrise = "6:00 AM",
            sunset = "6:00 PM",
            date = "06 February, 6:00 AM",
            weatherIcon = WeatherCondition.CLEAR,
            isNightTime = true
        ),
        WeatherHistoryItem(
            cityName = "Malolos",
            country = "Philippines",
            weatherCondition = "Scattered Clouds",
            windSpeed = "14.10",
            windGust = "28.20",
            humidity = "34.10",
            temperature = "30.0°",
            sunrise = "6:00 AM",
            sunset = "6:00 PM",
            date = "07 February, 7:00 AM",
            weatherIcon = WeatherCondition.CLOUDY
        ),
        WeatherHistoryItem(
            cityName = "Malolos",
            country = "Philippines",
            weatherCondition = "Thunderstorm",
            windSpeed = "14.10",
            windGust = "28.20",
            humidity = "34.10",
            temperature = "30.0°",
            sunrise = "6:00 AM",
            sunset = "6:00 PM",
            date = "07 February, 8:30 AM",
            weatherIcon = WeatherCondition.RAINY,
            isNightTime = true
        ),
    )
}