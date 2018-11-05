package com.nikita.bulygin.weatherapp.di


import com.nikita.bulygin.weatherapp.di.components.AppComponent

interface InjectableApplication {
    fun getAppComponent(): AppComponent
}
