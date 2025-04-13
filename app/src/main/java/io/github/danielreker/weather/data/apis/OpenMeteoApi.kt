package io.github.danielreker.weather.data.apis

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    data class HourlyForecastResponse(
        @SerializedName("hourly") val hourly: HourlyData,
        @SerializedName("timezone") val timezone: String,
    ) {
        data class HourlyData(
            @SerializedName("time") val time: List<Long>,
            @SerializedName("temperature_2m") val temperature2m: List<Double>,
            @SerializedName("weather_code") val weatherCode: List<Int>,
            @SerializedName("wind_speed_10m") val windSpeed10m: List<Double>,
            @SerializedName("relative_humidity_2m") val relativeHumidity2m: List<Int>,
        )
    }

    @GET("v1/forecast?hourly=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m&timeformat=unixtime&timezone=auto")
    suspend fun getHourlyForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("forecast_days") forecastDays: Int = 1
    ): HourlyForecastResponse
}