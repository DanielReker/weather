package io.github.danielreker.weather.data.datasources

import io.github.danielreker.weather.data.apis.ApiNinjasCityApi
import io.github.danielreker.weather.models.City

class RemoteCityDataSource(
    private val api: ApiNinjasCityApi
) {
    suspend fun searchCities(name: String): List<City> {
        val response = api.searchCities(name)
        return response.map { cityData ->
            City(
                name = cityData.name,
                lat = cityData.lat,
                lon = cityData.lon,
            )
        }
    }
}