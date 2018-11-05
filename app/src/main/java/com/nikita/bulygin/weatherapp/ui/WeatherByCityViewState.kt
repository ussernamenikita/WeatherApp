package com.nikita.bulygin.weatherapp.ui

import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.domain.entities.Weather


data class WeatherByCityViewState (var currentError :String? = null,
    var weatherList: List<Weather>? = null,
    var citiesList: List<City>? = null)
