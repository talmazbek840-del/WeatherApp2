package temirlan.com.weatherapp.domain

import temirlan.com.weatherapp.domain.model.CurrentWeather

class GetWeatherUseCase(private val repository: CurrentWeatherRepository) {
    suspend operator fun invoke(city: String): CurrentWeather {
        return repository.getWeather(city)
    }
}

