package com.nikita.bulygin.weatherapp.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.nikita.bulygin.weatherapp.domain.IWeatherInteractor
import com.nikita.bulygin.weatherapp.utils.ContextUtils
import com.nikita.bulygin.weatherapp.vm.WeatherViewModel

import javax.inject.Inject
import javax.inject.Named

import io.reactivex.Scheduler


class WeatherViewModelFactory @Inject
constructor(application: Application,
            private val weatherInteractor: IWeatherInteractor,
            @param:Named(DIConstants.UI) private val uiScheduler: Scheduler,
            private val contextUtils: ContextUtils) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.name == WeatherViewModel::class.java.name) {
            WeatherViewModel(weatherInteractor, uiScheduler, contextUtils) as T
        } else super.create(modelClass)
    }
}
