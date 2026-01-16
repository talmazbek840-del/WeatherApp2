package temirlan.com.weatherapp.domain.model

sealed interface WeatherUiState {
    object Idle : WeatherUiState
    object Loading : WeatherUiState
    data class Success(
        val current: CurrentWeather,
        val forecast: List<ForecastDailyWeather>
    ) : WeatherUiState

    data class Error(val message: String) : WeatherUiState
}