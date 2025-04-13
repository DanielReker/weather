package io.github.danielreker.weather.data.apis

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiNinjasCityApi {
//    data class CitiesResponse(
//        val cities: List<CityData>,
//    ) {
//        data class CityData(
//            @SerializedName("name") val name: String,
//            @SerializedName("latitude") val lat: Double,
//            @SerializedName("longitude") val lon: Double,
//        )
//    }

    data class CityData(
        @SerializedName("name") val name: String,
        @SerializedName("latitude") val lat: Double,
        @SerializedName("longitude") val lon: Double,
    )


    @Headers("X-Api-Key: 4BUjJSPCWhIROuWkZmPbDA==D5tNOsUgn33PYTzn")
    @GET("v1/city")
    suspend fun searchCities(
        @Query("name") name: String
    ): List<CityData>
}