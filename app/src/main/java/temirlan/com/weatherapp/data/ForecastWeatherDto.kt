package temirlan.com.weatherapp.data

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    val forecast: ForecastDto
)

data class ForecastDto(
    @SerializedName("forecastday")
    val forecastDayList: List<ForecastDayDto>
)

data class ForecastDayDto(
    val date: String,
    val day: DayDto
)

data class DayDto(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double
)