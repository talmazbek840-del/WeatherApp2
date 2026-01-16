package temirlan.com.weatherapp.data

import temirlan.com.weatherapp.domain.model.CurrentWeather
import temirlan.com.weatherapp.domain.CurrentWeatherRepository

class CurrentWeatherRepositoryImpl (
    private val api: WeatherAPI,
    private val mapper: WeatherMapper
) : CurrentWeatherRepository {
    override suspend fun getWeather(city: String): CurrentWeather {
        try {
            val response = api.getCurrentWeather(city)
            return mapper.mapToCurrentWeather(response)
        }
        catch (e: Exception){
            throw e
        }

    }
}
