package io.github.danielreker.weather.models

data class City(
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
)
