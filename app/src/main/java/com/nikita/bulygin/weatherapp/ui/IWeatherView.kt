package com.nikita.bulygin.weatherapp.ui


interface IWeatherView {
    fun updateState(state : WeatherByCityViewState)
}