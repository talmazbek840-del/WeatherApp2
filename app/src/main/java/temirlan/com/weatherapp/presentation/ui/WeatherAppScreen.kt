package temirlan.com.weatherapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import temirlan.com.weatherapp.R
import temirlan.com.weatherapp.domain.model.CurrentWeather
import temirlan.com.weatherapp.domain.model.ForecastDailyWeather
import temirlan.com.weatherapp.domain.model.WeatherUiState
import temirlan.com.weatherapp.presentation.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppScreen(viewModel: WeatherViewModel = koinViewModel()) {

    var inputCity by remember { mutableStateOf("Bishkek") }
    var selectedCity by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            SearchBar(
                city = inputCity,
                onCityChange = { inputCity = it },
                onSearch = {
                    viewModel.loadWeather(inputCity)

                })
            when (uiState) {
                is WeatherUiState.Idle -> {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.enter_city_name)
                    )
                }

                is WeatherUiState.Loading -> {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.loading)
                    )
                }

                is WeatherUiState.Success -> {
                    val currentWeatherState = (uiState as WeatherUiState.Success).current
                    val forecastState = (uiState as WeatherUiState.Success).forecast
                    selectedCity = currentWeatherState.location
                    CurrentWeatherCard(
                        city = selectedCity,
                        currentWeather = currentWeatherState
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        forecastState.forEach {
                            ForecastWeatherCard(
                                forecastDailyWeather = it
                            )
                        }
                    }
                }

                is WeatherUiState.Error -> {
                    ErrorScreen(
                        message = (uiState as WeatherUiState.Error).message
                    )
                }
            }


        }
    }

}

@Composable
fun SearchBar(
    city: String, onCityChange: (String) -> Unit, onSearch: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = onCityChange,
                label = { Text(stringResource(R.string.enter_city)) },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(1.dp))
            IconButton(onClick = onSearch) {
                Icon(Icons.Default.Search, contentDescription = "")
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(
    city: String,
    currentWeather: CurrentWeather?
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column {

                Text(
                    text = city,
                    style = MaterialTheme.typography.headlineLarge
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(64.dp),
                            model = currentWeather?.iconUrl,
                            contentDescription = null
                        )
                        Text(
                            text = "${currentWeather?.temperature}${stringResource(R.string.Celsia)}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Column {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "${currentWeather?.description}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "${stringResource(R.string.feels_like)} ${currentWeather?.feelsLike}${
                                stringResource(
                                    R.string.Celsia
                                )
                            }",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ParamsShowScreen(

                    stringResource(R.string.humidity),
                    "${currentWeather?.humidity}${stringResource(R.string.percent)}"
                )
                ParamsShowScreen(
                    stringResource(R.string.wind_speed),
                    "${currentWeather?.windSpeed}${stringResource(R.string.km_h)}"
                )
            }
        }
    }
}

@Composable
fun ForecastWeatherCard(
    forecastDailyWeather: ForecastDailyWeather?
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(
                2.dp,
                shape = CardDefaults.shape
            ),
        shape = CardDefaults.shape,
    ) {
        Column(

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .padding(4.dp),
                text = forecastDailyWeather?.date ?: "",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = "${forecastDailyWeather?.minTemp.toString()}${stringResource(R.string.Celsia)}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = "${forecastDailyWeather?.maxTemp.toString()}${stringResource(R.string.Celsia)}",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun ParamsShowScreen(
    description: String,
    value: String
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = CardDefaults.shape
    ) {
        Row {
            Text(
                modifier = Modifier.padding(2.dp),
                text = description,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(2.dp),
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.titleLarge
        )
    }
}