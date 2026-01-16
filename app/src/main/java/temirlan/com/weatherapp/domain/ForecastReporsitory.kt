package temirlan.com.weatherapp.domain

import temirlan.com.weatherapp.domain.model.ForecastDailyWeather

interface ForecastWeatherRepository {
    suspend fun getForecastWeather(city: String): List<ForecastDailyWeather>
}