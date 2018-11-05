package com.nikita.bulygin.weatherapp.vm

import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.ui.IWeatherView


interface IWeatherViewModel {
    fun bindView(view: IWeatherView)
    fun unbindView(view: IWeatherView)
    fun onCitySelected(city: City)
    fun onCityNameChanged(name: String)
}
