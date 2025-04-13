package io.github.danielreker.weather

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.danielreker.weather.data.apis.OpenMeteoApi
import io.github.danielreker.weather.data.datasources.RemoteWeatherDataSource
import io.github.danielreker.weather.data.repositories.WeatherRepository
import io.github.danielreker.weather.data.repositories.WeatherRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
    @Provides
    fun provideOpenMeteoApi(): OpenMeteoApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_METEO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoApi::class.java)
    }

    @Provides
    fun provideWeatherRepository(
        api: OpenMeteoApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            dataSource = RemoteWeatherDataSource(api),
        )
    }
}