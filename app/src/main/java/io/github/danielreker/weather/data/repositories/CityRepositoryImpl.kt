package io.github.danielreker.weather.data.repositories

import io.github.danielreker.weather.data.datasources.RemoteCityDataSource
import io.github.danielreker.weather.models.City

class CityRepositoryImpl(
    private val dataSource: RemoteCityDataSource
) : CityRepository {
    private val cityNames = listOf(
        "Izhevsk",
        "Moscow",
        "Saint Petersburg",
        "Ekaterinburg",
        "Kazan",
        "Novosibirsk",
    )

    private val cities: MutableList<City> = mutableListOf()

    override suspend fun getCities(): List<City> {
        if (cities.isEmpty()) {
            cityNames.forEach { cityName -> cities += dataSource.searchCities(cityName) }
        }

        return cities
    }
}