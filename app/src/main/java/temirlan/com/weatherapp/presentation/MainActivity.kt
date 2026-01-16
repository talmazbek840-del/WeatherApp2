package temirlan.com.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import temirlan.com.weatherapp.presentation.theme.WeatherAppTheme
import temirlan.com.weatherapp.presentation.ui.WeatherAppScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme() {
                WeatherAppScreen()
            }
        }
    }
}


