package temirlan.com.weatherapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import temirlan.com.weatherapp.di.mapperModule
import temirlan.com.weatherapp.di.networkModule
import temirlan.com.weatherapp.di.repositoryModule
import temirlan.com.weatherapp.di.useCaseModule
import temirlan.com.weatherapp.di.viewModelModule

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                mapperModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}