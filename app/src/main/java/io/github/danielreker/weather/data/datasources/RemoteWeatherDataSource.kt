package io.github.danielreker.weather.data.datasources

import io.github.danielreker.weather.data.apis.OpenMeteoApi
import io.github.danielreker.weather.models.HourlyWeather
import io.github.danielreker.weather.models.WeatherCondition
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

class RemoteWeatherDataSource(
    private val api: OpenMeteoApi
) {
    suspend fun getHourlyForecast(lat: Double, lon: Double): List<HourlyWeather> {
        val response = api.getHourlyForecast(lat, lon)

        return response.hourly.time.indices.map { i ->
            HourlyWeather(
                timestamp = Instant.fromEpochSeconds(response.hourly.time[i]),
                timezone = TimeZone.of(response.timezone),
                temperatureDc = response.hourly.temperature2m[i],
                condition = WeatherCondition.fromWmoCode(response.hourly.weatherCode[i]),
                windSpeedMps = response.hourly.windSpeed10m[i],
                relativeHumidityPercent = response.hourly.relativeHumidity2m[i]
            )
        }
    }
}