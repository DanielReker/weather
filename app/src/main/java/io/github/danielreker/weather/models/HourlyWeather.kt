package io.github.danielreker.weather.models

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

data class HourlyWeather(
    val timestamp: Instant,
    val timezone: TimeZone,
    val temperatureDc: Double,
    val condition: WeatherCondition,
    val windSpeedMps: Double,
    val relativeHumidityPercent: Int,
)
