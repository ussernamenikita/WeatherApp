package com.nikita.bulygin.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nikita.bulygin.weatherapp.di.InjectableActivity
import com.nikita.bulygin.weatherapp.di.InjectableApplication
import com.nikita.bulygin.weatherapp.di.components.ActivityComponent
import com.nikita.bulygin.weatherapp.di.modules.ActivityModule
import com.nikita.bulygin.weatherapp.ui.WeatherFragment

class activity : AppCompatActivity(), InjectableActivity {


    lateinit var component: ActivityComponent

    override fun getActivityComponent(): ActivityComponent {
        return this.component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initiateComponent()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WeatherFragment()).commit()
        }
    }

    private fun initiateComponent() {
        val app = application
        if (app is InjectableApplication) {
            component = app.
                    getAppComponent().getActivityComponent(ActivityModule(this))
        }
    }
}

