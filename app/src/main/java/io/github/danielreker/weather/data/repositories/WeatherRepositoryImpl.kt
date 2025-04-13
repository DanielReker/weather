package io.github.danielreker.weather.data.repositories

import io.github.danielreker.weather.data.datasources.RemoteWeatherDataSource
import io.github.danielreker.weather.models.City
import io.github.danielreker.weather.models.HourlyWeather

class WeatherRepositoryImpl(
    private val dataSource: RemoteWeatherDataSource,
) : WeatherRepository {
    override suspend fun getHourlyForecast(city: City): List<HourlyWeather> {
        return dataSource.getHourlyForecast(city.lat, city.lon)
    }
}