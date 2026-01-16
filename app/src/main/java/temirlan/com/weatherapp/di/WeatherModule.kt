package temirlan.com.weatherapp.di

import org.koin.dsl.module
import temirlan.com.weatherapp.domain.GetForecastUseCase
import temirlan.com.weatherapp.domain.GetWeatherUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import temirlan.com.weatherapp.data.CurrentWeatherRepositoryImpl
import temirlan.com.weatherapp.data.ForecastWeatherRepositoryImpl
import temirlan.com.weatherapp.data.WeatherAPI
import temirlan.com.weatherapp.data.WeatherMapper
import temirlan.com.weatherapp.domain.CurrentWeatherRepository
import temirlan.com.weatherapp.domain.ForecastWeatherRepository
import temirlan.com.weatherapp.presentation.viewmodel.WeatherViewModel

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()

    }
    single {
        Retrofit.Builder()
            .baseUrl(WeatherAPI.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<WeatherAPI> {
        get<Retrofit>().create(WeatherAPI::class.java)
    }
}

val mapperModule = module {
    single {
        WeatherMapper()
    }
}

val repositoryModule = module {

    single<CurrentWeatherRepository> {
        CurrentWeatherRepositoryImpl(get(), get())
    }

    single<ForecastWeatherRepository> {
        ForecastWeatherRepositoryImpl(get(), get())
    }
}

val useCaseModule = module {
    single {
        GetWeatherUseCase(get())
    }
    single {
        GetForecastUseCase(get())
    }

}

val viewModelModule = module {
    viewModel {
        WeatherViewModel(get(), get())
    }
}


