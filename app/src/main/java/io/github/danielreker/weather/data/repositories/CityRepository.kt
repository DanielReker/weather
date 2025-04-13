package io.github.danielreker.weather.data.repositories

import io.github.danielreker.weather.models.City

interface CityRepository {
    suspend fun getCities(): List<City>
}