package temirlan.com.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String = API_KEY
    ): CurrentWeatherResponseDto

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String = API_KEY,
        @Query("days") days: Int = FORECAST_DAYS_COUNT
    ): ForecastResponseDto

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        const val API_KEY = "9bab464177c34409aec100137252612"
        const val FORECAST_DAYS_COUNT = 3
    }
}
