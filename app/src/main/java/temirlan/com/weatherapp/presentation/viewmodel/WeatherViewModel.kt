package temirlan.com.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import temirlan.com.weatherapp.domain.GetForecastUseCase
import temirlan.com.weatherapp.domain.GetWeatherUseCase
import temirlan.com.weatherapp.domain.model.WeatherUiState
import java.io.IOException

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState = _uiState.asStateFlow()
    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val current = getWeatherUseCase(city)
                val forecast = getForecastUseCase(city)
                _uiState.value = WeatherUiState.Success(
                    current = current,
                    forecast = forecast
                )
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "Invalid city name"
                    401 -> "Invalid API key"
                    500 -> "Server error"
                    else -> "Error ${e.code()}"
                }
                _uiState.value = WeatherUiState.Error(message)

            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error(
                    "No internet connection"
                )
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(
                    "Unexpected error"
                )
            }
        }
    }
}
