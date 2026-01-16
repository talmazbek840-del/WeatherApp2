package temirlan.com.weatherapp.data

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponseDto(
    @SerializedName("current")
    val currentDto: CurrentDto,
    @SerializedName("location")
    val locationDto: LocationDto?
)

data class LocationDto(
    @SerializedName("region")
    val name: String
)

data class CurrentDto(
    @SerializedName("temp_c")
    val tempC: Double,
    val condition: Condition?,
    val humidity: Int,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
)

data class Condition(
    val text: String?,
    val icon: String?
)
