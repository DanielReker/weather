package io.github.danielreker.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.danielreker.weather.data.repositories.WeatherRepository
import io.github.danielreker.weather.models.City
import io.github.danielreker.weather.models.HourlyWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HourlyWeatherUiState(
    val data: HourlyWeather,
    val isCurrent: Boolean = false
)

data class WeatherUiState(
    val isLoading: Boolean = false,
    val forecast: List<HourlyWeatherUiState> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<Map<City, WeatherUiState>>(emptyMap())
    val uiState = _uiState.asStateFlow()

    fun loadWeatherForCity(city: City) {
        if (_uiState.value[city]?.forecast?.isNotEmpty() == true) return

        _uiState.update { current ->
            current + (city to WeatherUiState(isLoading = true))
        }

        viewModelScope.launch {
            try {
                val forecast = weatherRepository.getHourlyForecast(city)
                _uiState.update { current ->
                    current + (city to WeatherUiState(forecast = forecast.map { HourlyWeatherUiState(it) }, isLoading = false))
                }
            } catch (e: Exception) {
                _uiState.update { current ->
                    current + (city to WeatherUiState(isLoading = false, error = e.message))
                }
            }
        }
    }

    fun loadWeatherForCities(cities: List<City>) {
        cities.forEach { loadWeatherForCity(it) }
    }
}