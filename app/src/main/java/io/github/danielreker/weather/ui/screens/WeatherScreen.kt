package io.github.danielreker.weather.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.danielreker.weather.R
import io.github.danielreker.weather.data.datasources.CityProvider
import io.github.danielreker.weather.models.WeatherCondition
import io.github.danielreker.weather.ui.theme.WeatherTheme
import io.github.danielreker.weather.ui.viewmodels.HourlyWeatherUiState
import io.github.danielreker.weather.ui.viewmodels.WeatherUiState
import io.github.danielreker.weather.ui.viewmodels.WeatherViewModel
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import kotlinx.datetime.offsetIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val cityProvider = CityProvider()
    val cities = cityProvider.getCities()
    val cityWeatherMap by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = {cities.size})

    LaunchedEffect(Unit) {
        viewModel.loadWeatherForCities(cities)
    }

    WeatherTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(cities[pagerState.currentPage].name) }
                )
            }
        ) { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) { pageIndex ->
                val city = cities[pageIndex]
                val cityState = cityWeatherMap[city]

                WeatherCard(cityState)
            }
        }
    }
}

@Composable
fun WeatherCard(
    state: WeatherUiState?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state == null || state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.forecast.size) { i ->
                    HourlyWeatherCard(
                        modifier = Modifier.padding(vertical = 8.dp),
                        hourlyWeather = state.forecast[i]
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherConditionIcon(weatherCondition: WeatherCondition, modifier: Modifier = Modifier) {
    val res = when (weatherCondition) {
        WeatherCondition.SUNNY -> R.drawable.wi_sunny
        WeatherCondition.CLOUDY -> R.drawable.wi_cloudy
        WeatherCondition.FOGGY -> R.drawable.wi_foggy
        WeatherCondition.RAINY -> R.drawable.wi_rainy
        WeatherCondition.SNOWY -> R.drawable.wi_snowy
        WeatherCondition.STORMY -> R.drawable.wi_stormy
        WeatherCondition.UNKNOWN -> R.drawable.wi_unknown
    }

    Icon(
        painter = painterResource(res),
        contentDescription = weatherCondition.name,
        modifier = modifier
    )
}

@Composable
fun WeatherParameterIcon(
    text: String,
    @DrawableRes drawableRes: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(drawableRes),
            contentDescription = "Temperature, °C",
            modifier = Modifier.width(32.dp).height(32.dp).padding(bottom = 4.dp)
        )
        Text(text)
    }
}

@Composable
fun HourlyWeatherCard(
    hourlyWeather: HourlyWeatherUiState,
    modifier: Modifier = Modifier
) {
    val timeFormat = DateTimeComponents.Format {
        hour(); char(':'); minute()
    }

    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherConditionIcon(
                    weatherCondition = hourlyWeather.data.condition,
                    modifier = Modifier.width(48.dp).height(48.dp)
                )
                Text(
                    text = hourlyWeather.data.timestamp.format(timeFormat, hourlyWeather.data.timestamp.offsetIn(hourlyWeather.data.timezone)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            WeatherParameterIcon(
                text = "${hourlyWeather.data.temperatureDc} °C",
                drawableRes = R.drawable.temperature_icon
            )
            WeatherParameterIcon(
                text = "${hourlyWeather.data.windSpeedMps} m/s",
                drawableRes = R.drawable.wind_speed_icon
            )
            WeatherParameterIcon(
                text = "${hourlyWeather.data.relativeHumidityPercent}%",
                drawableRes = R.drawable.humidity_icon
            )
        }
    }
}
