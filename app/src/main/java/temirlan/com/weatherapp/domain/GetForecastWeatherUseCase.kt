package temirlan.com.weatherapp.domain

import temirlan.com.weatherapp.domain.model.ForecastDailyWeather

class GetForecastUseCase(
    private val repository: ForecastWeatherRepository
) {
    suspend operator fun invoke(city: String): List<ForecastDailyWeather> {
        return repository.getForecastWeather(city)
    }
}