package temirlan.com.weatherapp.domain

import temirlan.com.weatherapp.domain.model.CurrentWeather

interface CurrentWeatherRepository {
    suspend fun getWeather(city: String): CurrentWeather
}