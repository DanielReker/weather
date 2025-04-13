package io.github.danielreker.weather.data.repositories

import io.github.danielreker.weather.models.City
import io.github.danielreker.weather.models.HourlyWeather

interface WeatherRepository {
    suspend fun getHourlyForecast(city: City): List<HourlyWeather>
}