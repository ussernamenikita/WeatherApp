package com.nikita.bulygin.weatherapp.di

import com.nikita.bulygin.weatherapp.di.components.ActivityComponent


interface InjectableActivity {
    fun getActivityComponent():ActivityComponent
}