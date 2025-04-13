package io.github.danielreker.weather.data.datasources

import io.github.danielreker.weather.models.City

class CityProvider {
    private val cities = listOf(
        City(1, "Izhevsk", 56.84976, 53.20448),
        City(2, "Moscow", 55.75222, 37.61556),
        City(3, "Saint Petersburg", 59.93863, 30.31413)
    )

    fun getCities(): List<City> {
        return cities
    }
}