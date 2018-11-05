package com.nikita.bulygin.weatherapp.vm


import android.arch.lifecycle.ViewModel
import android.util.Log
import com.nikita.bulygin.weatherapp.R
import com.nikita.bulygin.weatherapp.domain.DomainResponse
import com.nikita.bulygin.weatherapp.domain.IWeatherInteractor
import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.domain.entities.Weather
import com.nikita.bulygin.weatherapp.ui.IWeatherView
import com.nikita.bulygin.weatherapp.ui.WeatherByCityViewState
import com.nikita.bulygin.weatherapp.utils.ContextUtils
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class WeatherViewModel(private var weatherInteractor: IWeatherInteractor,
                       private var uiScheduler: Scheduler,
                       private var contextUtils: ContextUtils) : ViewModel(), IWeatherViewModel {

    private var currentViewState = WeatherByCityViewState()

    private val TAG = "WeatherViewModel"

    private var lastSelectedCitySubscription: Disposable? = null

    private var lastCityNameSubscription: Disposable? = null

    private var allSubscriptions = CompositeDisposable()


    private var view: IWeatherView? = null

    override fun bindView(view: IWeatherView) {
        this.view = view
        updateUI()
    }

    override fun unbindView(view: IWeatherView) {
        if (this.view != null && this.view!! == view) {
            this.view = null
        } else {
            Log.d(TAG, "unbindView : unknown view")
        }
    }

    override fun onCitySelected(city: City) {
        if (this.lastSelectedCitySubscription != null) {
            allSubscriptions.remove(this.lastSelectedCitySubscription ?: return)
        }
        this.lastSelectedCitySubscription = this.weatherInteractor.
                getWeather(city).
                observeOn(uiScheduler)
                .subscribe({
                    currentViewState = currentViewState.copy(weatherList = it.data ?: ArrayList<Weather>())
                    if (it?.status == DomainResponse.RESULT_STATUS.ERROR) {
                        showLodingWeatherError(city)
                    } else {
                        updateUI()
                    }
                }, {
                    Log.d(TAG, "Error while try get weather fot city " + city.name)
                    showLodingWeatherError(city)
                })
    }

    private fun showLodingWeatherError(city: City) {
        currentViewState = currentViewState.copy(currentError = contextUtils.getString(R.string.weather_by_city_weather_loading_error_text, city.name))
        updateUI()
    }


    private fun updateUI() {
        if (view != null) {
            this.view!!.updateState(currentViewState)
            currentViewState = currentViewState.copy(currentError = null)
        }
    }

    override fun onCityNameChanged(name: String) {
        if (lastCityNameSubscription != null) {
            allSubscriptions.remove(lastCityNameSubscription ?: return)
        }
        this.lastCityNameSubscription = weatherInteractor.
                getCitiesForPrefix(name).
                observeOn(uiScheduler).
                subscribe({
                    val resultList = it.data ?: ArrayList<City>()
                    currentViewState = currentViewState.copy(citiesList = resultList)
                    if (it?.status == DomainResponse.RESULT_STATUS.ERROR) {
                        showLodingCitiesError(name)
                    } else {
                        updateUI()
                    }
                }, {
                    it.printStackTrace()
                    showLodingCitiesError(name)
                    Log.d(TAG, "Error while try loding cities for prefix " + name)
                })
    }

    private fun showLodingCitiesError(name: String) {
        val error = contextUtils.getString(R.string.weather_by_city_cities_loading_error_text, name)
        currentViewState = currentViewState.copy(currentError = error)
        updateUI()
    }

    override fun onCleared() {
        super.onCleared()
        allSubscriptions.dispose()
    }
}