package io.github.danielreker.weather.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.danielreker.weather.data.repositories.CityRepository
import io.github.danielreker.weather.models.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CityUiState(
    val cities: List<City> = emptyList(),
)

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCities()
    }

    private fun loadCities() = viewModelScope.launch {
        _uiState.update { it.copy(cities = cityRepository.getCities()) }
    }
}