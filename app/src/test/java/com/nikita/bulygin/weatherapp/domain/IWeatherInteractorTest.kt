package com.nikita.bulygin.weatherapp.domain

import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.domain.entities.Weather
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class IWeatherInteractorTest {

    private var cityRepository = CityRepositoryStub()
    private var weatherRepository = WeatherRepositoryStub()
    private lateinit var interactor: IWeatherInteractor
    private val testObserver = TestObserver<DomainResponse<List<Weather>>>()
    private val city = City(0, "some city", "RU")

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactor = WeatherInteractorImpl(weatherRepository, cityRepository)
    }

    @Test
    @Throws(Exception::class)
    fun getWeatherUseCityForRepo() {
        interactor.getWeather(city)
        assertEquals(city, weatherRepository.lastGetWeatherFromCity)
    }

    @Test
    fun getWeatherSuccess() {
        val result = interactor.getWeather(city)
        val response = DomainResponse<MutableList<Weather>>(ArrayList(), 0, DomainResponse.RESULT_STATUS.SUCCESS)
        weatherRepository.postWeather(response)
        result.subscribe(testObserver)
        assertEquals(response.status, testObserver.values()[0].status)
    }


    @Test
    fun getWeatherError() {
        val result = interactor.getWeather(city)
        weatherRepository.postError(java.lang.Exception("Test"))
        result.subscribe(testObserver)
        testObserver.assertError { it.message.equals("Test") }
    }

    @Test
    @Throws(Exception::class)
    fun getCitiesForPrefixGetCityFromRepo() {
        val prefix = "prefix"
        interactor.getCitiesForPrefix(prefix)
        assertEquals(prefix, cityRepository.lastgetPossibleCitiesPrefix)
    }

    @Test
    fun getCitySuccess() {
        val prefix = "prefix"
        val result = interactor.getCitiesForPrefix(prefix)
        val testObserver = TestObserver<DomainResponse<List<City>>>()
        result.subscribe(testObserver)
        val response = DomainResponse<MutableList<City>>(ArrayList(), ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS)
        cityRepository.postCitiesList(response)
        assertEquals(response, testObserver.values()[0])
    }

    @Test
    fun getCityError() {
        val prefix = "prefix"
        val result = interactor.getCitiesForPrefix(prefix)
        val testObserver = TestObserver<DomainResponse<List<City>>>()
        result.subscribe(testObserver)
        cityRepository.postError(java.lang.Exception("Test"))
        testObserver.assertErrorMessage("Test")
    }
}

class WeatherRepositoryStub : IWeatherRepository {

    var lastGetWeatherFromCity: City? = null


    private val weather = BehaviorSubject.create<DomainResponse<MutableList<Weather>>>();

    override fun getWeatherFromCity(city: City?): Observable<DomainResponse<MutableList<Weather>>> {
        this.lastGetWeatherFromCity = city
        return weather
    }

    fun postError(throwable: Throwable) {
        weather.onError(throwable)
    }

    fun postWeather(response: DomainResponse<MutableList<Weather>>) {
        weather.onNext(response)
    }
}

class CityRepositoryStub : ICityRepository {

    private val cities = BehaviorSubject.create<DomainResponse<MutableList<City>>>()

    var lastgetPossibleCitiesPrefix: String? = null

    override fun getPossibleCities(prefix: String?): Observable<DomainResponse<MutableList<City>>> {
        this.lastgetPossibleCitiesPrefix = prefix
        return cities
    }

    fun postCitiesList(list: DomainResponse<MutableList<City>>) {
        cities.onNext(list)
    }

    fun postError(throwable: Throwable) {
        cities.onError(throwable)
    }
}
