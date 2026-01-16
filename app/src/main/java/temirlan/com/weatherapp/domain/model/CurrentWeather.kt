package temirlan.com.weatherapp.domain.model

class CurrentWeather(
    val temperature: Double,
    val description: String,
    val humidity: Double,
    val windSpeed: Double,
    val feelsLike: Double,
    val iconUrl: String,
    val location: String
)
