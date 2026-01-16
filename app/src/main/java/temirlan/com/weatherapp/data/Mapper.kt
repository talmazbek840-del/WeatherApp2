package temirlan.com.weatherapp.data

import temirlan.com.weatherapp.domain.model.CurrentWeather
import temirlan.com.weatherapp.domain.model.ForecastDailyWeather

class WeatherMapper {
    fun mapToCurrentWeather(dto: CurrentWeatherResponseDto): CurrentWeather {
        val current = dto.currentDto
        return CurrentWeather(
            temperature = current.tempC,
            description = current.condition?.text ?: "No data",
            humidity = current.humidity.toDouble(),
            windSpeed = current.windKph,
            feelsLike = current.feelsLikeC,
            iconUrl = current.condition?.icon
                ?.let { "https:$it" }
                ?: "",
            location = dto.locationDto?.name ?: ""
        )
    }

    fun mapToForecastWeather(dtoList: List<ForecastDayDto>): List<ForecastDailyWeather> {
        return dtoList.map { dto ->
            ForecastDailyWeather(
                minTemp = dto.day.minTempC,
                maxTemp = dto.day.maxTempC,
                date = dto.date.substring(5, 10).replace("-", ".")
            )
        }
    }
}

