package com.nikita.bulygin.weatherapp.vm

import android.content.Context
import android.text.TextUtils
import com.nikita.bulygin.weatherapp.domain.DomainResponse
import com.nikita.bulygin.weatherapp.domain.ErrorCodes
import com.nikita.bulygin.weatherapp.domain.IWeatherInteractor
import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.domain.entities.Weather
import com.nikita.bulygin.weatherapp.ui.IWeatherView
import com.nikita.bulygin.weatherapp.ui.WeatherByCityViewState
import com.nikita.bulygin.weatherapp.utils.ContextUtils
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.SingleSubject
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import java.util.*


class WeatherViewModelTest {

    private lateinit var viewModel: IWeatherViewModel

    private val weatherInteractor = WeatherInteratorStub()

    private val uiScheduler = TestScheduler()

    private val contextUtils = ContextUtilsStub(null)

    private val view = WeatherVIewStub()

    private val city = City(0, "", "")

    private val simpleWeather = Weather(Date(0), 0.0)

    private val simpleCity = City(0,"Moscow","RU")

    private val successRepsponse = DomainResponse(mutableListOf(simpleWeather), ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS)

    private val successCitiesResponse = DomainResponse(mutableListOf(simpleCity),ErrorCodes.NO_ERROR,DomainResponse.RESULT_STATUS.SUCCESS)

    private val nullableCitiesResponse = DomainResponse<MutableList<City>>(null,0,null)

    private val errorResponseCities = DomainResponse(mutableListOf(simpleCity),ErrorCodes.NETWORK_ERROR,DomainResponse.RESULT_STATUS.ERROR)

    private val errorResponseWeather = DomainResponse(mutableListOf(simpleWeather),ErrorCodes.NETWORK_ERROR,DomainResponse.RESULT_STATUS.ERROR)

    private val nullableWeatherResponse = DomainResponse<MutableList<Weather>>(null, 0, null)

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(weatherInteractor, uiScheduler, contextUtils)
        contextUtils.stringWithVararg = "test"
        contextUtils.stringWithoutParams = "test"
    }
    @Test
    fun bindViewCallUpdateUI() {
        assertNull(view.lastReceivedState)
        viewModel.bindView(view)
        assertNotNull(view.lastReceivedState)
    }

    //View binding
    @Test
    fun onCitySelectedCallInteractorMethod() {
        viewModel.onCitySelected(city)
        assertEquals(city, weatherInteractor.lastGetWeatherCity)
    }

    @Test
    fun onCitySelectedSubscribeToWeather() {
        viewModel.onCitySelected(city)
        assertTrue(weatherInteractor.weatherResponse.hasObservers())
    }

    //
    //Weather data
    @Test
    fun handleWeatherResponseWithNulls() {
        checkViewReceiveDataThenWeatherResponse(nullableWeatherResponse)
    }

    private fun checkViewStatus(success: DomainResponse.RESULT_STATUS) {
        assertNotNull(view.lastReceivedState)
        if(success == DomainResponse.RESULT_STATUS.SUCCESS){
            assertTrue(isEmpty(view.lastReceivedState!!.currentError))
        }else if(success == DomainResponse.RESULT_STATUS.ERROR){
            assertFalse(isEmpty(view.lastReceivedState!!.currentError))
        }
    }

    private fun isEmpty(currentError: String?): Boolean  = currentError == null || "" == currentError


    @Test
    fun onCitySelectedSuccessWeatherLoading() {
        checkViewReceiveDataThenWeatherResponse(successRepsponse)
    }

    @Test
    fun onCitySelectedErrorResponse() {
        checkViewReceiveDataThenWeatherResponse(errorResponseWeather)
    }

    @Test
    fun onCitySelectedErrorWeatherLoading() {
        checkViewReceiveDataThenWeatherResponse(Throwable("test"))
    }

    //

    //Cities data
    @Test
    fun onCityNameChangedResponseWithNulls() {
        checkViewReceivedDataThenCitiesResponse(nullableCitiesResponse)
    }


    @Test
    fun onCityNameChangedResponseSuccess() {
        checkViewReceivedDataThenCitiesResponse(successCitiesResponse)
    }

    @Test
    fun onCityNameChangedError() {
        checkViewReceivedDataThenCitiesResponse(Throwable("test"))
    }

    @Test
    fun onCityNameChangedResponseError() {
        checkViewReceivedDataThenCitiesResponse(errorResponseCities)
    }
    //




    private fun checkViewReceivedDataThenCitiesResponse(throwable: Throwable) {
        this.checkViewReceivedDataThenCitiesResponse(null,throwable)
    }
    private fun checkViewReceivedDataThenCitiesResponse(response: DomainResponse<MutableList<City>>?) {
        this.checkViewReceivedDataThenCitiesResponse(response,null)
    }

    private fun checkViewReceivedDataThenCitiesResponse(response: DomainResponse<MutableList<City>>?, throwable: Throwable?) {
        viewModel.bindView(view)
        viewModel.onCityNameChanged(city.name)
        view.lastReceivedState = null
        if (response != null) {
            weatherInteractor.citiesForPrefixResponse.onSuccess(response)
            uiScheduler.triggerActions()
            if(response.status != null) {
                val requestStatus : DomainResponse.RESULT_STATUS = response.status ?: return
                checkViewStatus(requestStatus)
            }
        } else if(throwable != null){
            weatherInteractor.citiesForPrefixResponse.onError(throwable)
            uiScheduler.triggerActions()
        }

        assertNotNull(view.lastReceivedState)
    }

    private fun checkViewReceiveDataThenWeatherResponse(response: DomainResponse<MutableList<Weather>>) {
        this.checkViewReceiveDataThenWeatherResponse(response, null)
    }

    private fun checkViewReceiveDataThenWeatherResponse(throwable: Throwable) {
        this.checkViewReceiveDataThenWeatherResponse(null, throwable)
    }


    private fun checkViewReceiveDataThenWeatherResponse(response: DomainResponse<MutableList<Weather>>?, throwable: Throwable?) {
        viewModel.bindView(view)
        viewModel.onCitySelected(city)
        view.lastReceivedState = null
        if (response != null) {
            weatherInteractor.weatherResponse.onSuccess(response)
            uiScheduler.triggerActions()
            if(response.status != null) {
                val requestStatus : DomainResponse.RESULT_STATUS = response.status ?: return
                checkViewStatus(requestStatus)
            }
        } else if(throwable != null){
            weatherInteractor.weatherResponse.onError(throwable)
            uiScheduler.triggerActions()
        }
        assertNotNull(view.lastReceivedState)
    }

}

class WeatherVIewStub : IWeatherView {

    var lastReceivedState: WeatherByCityViewState? = null

    override fun updateState(state: WeatherByCityViewState) {
        this.lastReceivedState = state
    }

}

class ContextUtilsStub(context: Context?) : ContextUtils(context) {


    var stringWithoutParams: String = ""

    var stringWithVararg: String = ""

    var lastStringid: Int? = null

    var lastVarargForString: Array<out Any?>? = null

    var lastStringIdWithVararg: Int? = null

    override fun getString(id: Int): String {
        this.lastStringid = id
        return this.stringWithoutParams
    }

    override fun getString(resId: Int, vararg vararg: Any?): String {
        this.lastStringIdWithVararg = resId
        this.lastVarargForString = vararg
        return this.stringWithVararg
    }
}

class WeatherInteratorStub : IWeatherInteractor {
    var lastGetWeatherCity: City? = null

    var lastGetCitiesForPrefix: String? = null

    val citiesForPrefixResponse = SingleSubject.create<DomainResponse<MutableList<City>>>()

    var weatherResponse = SingleSubject.create<DomainResponse<MutableList<Weather>>>()

    override fun getWeather(city: City?): Single<DomainResponse<MutableList<Weather>>> {
        this.lastGetWeatherCity = city
        return weatherResponse
    }

    override fun getCitiesForPrefix(prefix: String?): Single<DomainResponse<MutableList<City>>> {
        this.lastGetCitiesForPrefix = prefix
        return citiesForPrefixResponse
    }

}
