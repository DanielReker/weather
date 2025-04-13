package io.github.danielreker.weather.models

enum class WeatherCondition {
    SUNNY,
    CLOUDY,
    FOGGY,
    RAINY,
    SNOWY,
    STORMY,
    UNKNOWN;

    companion object {
        fun fromWmoCode(wmoCode: Int): WeatherCondition {
            return when (wmoCode) {
                0, 1 -> SUNNY
                2, 3 -> CLOUDY
                45, 48 -> FOGGY
                51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> RAINY
                71, 73, 75, 77, 85, 86 -> SNOWY
                95, 96, 99 -> STORMY
                else -> UNKNOWN
            }
        }
    }
}