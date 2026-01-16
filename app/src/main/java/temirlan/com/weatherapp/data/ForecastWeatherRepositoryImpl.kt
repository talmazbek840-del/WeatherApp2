package temirlan.com.weatherapp.data

import temirlan.com.weatherapp.domain.model.ForecastDailyWeather
import temirlan.com.weatherapp.domain.ForecastWeatherRepository

class ForecastWeatherRepositoryImpl(
    private val api: WeatherAPI,
    private val mapper: WeatherMapper
): ForecastWeatherRepository {
    override suspend fun getForecastWeather(city: String): List<ForecastDailyWeather> {
        try {
            val response = api.getForecastWeather(city)
            return mapper.mapToForecastWeather(response.forecast.forecastDayList)
        }
        catch (e: Exception)
        {
            throw e
        }
    }
}