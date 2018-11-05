package com.nikita.bulygin.weatherapp.vm

import android.arch.lifecycle.LiveData
import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.ui.IWeatherView
import com.nikita.bulygin.weatherapp.ui.WeatherByCityViewState


interface IWeatherViewModel {
    fun bindView(view: IWeatherView)
    fun unbindView(view: IWeatherView)
    fun onCitySelected(city: City)
    fun onCityNameChanged(name: String)
}
