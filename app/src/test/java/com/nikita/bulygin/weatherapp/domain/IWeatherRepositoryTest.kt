package com.nikita.bulygin.weatherapp.domain

import com.nikita.bulygin.weatherapp.data.WeatherRepositoryImpl
import com.nikita.bulygin.weatherapp.data.db.DBWeather
import com.nikita.bulygin.weatherapp.data.db.WeatherDao
import com.nikita.bulygin.weatherapp.data.network.api.WeatherApi
import com.nikita.bulygin.weatherapp.data.network.pojo.DailyWeatherResponse
import com.nikita.bulygin.weatherapp.domain.entities.City
import com.nikita.bulygin.weatherapp.domain.entities.Weather
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.SingleSubject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import org.junit.Before
import org.junit.Test


class IWeatherRepositoryTest {

    private lateinit var repository: IWeatherRepository

    private val appId = "appid"

    private val weatherApiStub = WeatherApiStub()

    private val networkScheduler = TestScheduler()

    private val weatherDaoStub = WeatherDaoStub()

    private val city = City(999, "test", "RU")

    private val testObserver = TestObserver<DomainResponse<List<Weather>>>()

    private val dailyWeather = DailyWeatherResponse()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        repository = WeatherRepositoryImpl(weatherApiStub, networkScheduler, weatherDaoStub, appId)
    }

    @Test
    @Throws(Exception::class)
    fun getWeatherFromCityNetworkSuccess() {
        val result = repository.getWeatherFromCity(city)
        result.subscribe(testObserver)
        weatherApiStub.onNextWeather5_3(dailyWeather)
        networkScheduler.triggerActions()
        assertEquals(1, testObserver.values().size)
        assertEquals(DomainResponse.RESULT_STATUS.SUCCESS, testObserver.values()[0].status)
    }

    @Test
    @Throws(Exception::class)
    fun getWeatherFromCityNetworkError() {
        val result = repository.getWeatherFromCity(city)
        result.subscribe(testObserver)
        weatherApiStub.onErrorWeather5_3(Throwable("test"))
        networkScheduler.triggerActions()
        assertEquals(1, testObserver.values().size)
        assertEquals(DomainResponse.RESULT_STATUS.ERROR, testObserver.values()[0].status)
    }


    @Test
    @Throws(Exception::class)
    fun getWeatherFromCityIfNetworkErrorReturnFromDatabase() {
        val result = repository.getWeatherFromCity(city)
        result.subscribe(testObserver)
        weatherApiStub.onErrorWeather5_3(Throwable("test"))
        val arrayFromDb = arrayListOf<DBWeather>(DBWeather(0, 0, 0.0, 0.0, 0.0, 0.0, 0, "", "", ""))
        weatherDaoStub.blockingResult = arrayFromDb
        networkScheduler.triggerActions()
        assertEquals(1, testObserver.values().size)
        assertEquals(DomainResponse.RESULT_STATUS.ERROR, testObserver.values()[0].status)
        assertEquals(1, testObserver.values()[0].data!!.size)
    }

    @Test
    fun getWeatherReturnCachedResponse() {
        val result = repository.getWeatherFromCity(city)
        val result2 = repository.getWeatherFromCity(city)
        assertEquals(result, result2)
        weatherApiStub.onErrorWeather5_3(Throwable("test"))
        networkScheduler.triggerActions()
        val result3 = repository.getWeatherFromCity(city)
        assertNotSame(result, result3)
    }
}

class WeatherDaoStub : WeatherDao() {

    override fun deleteOlderThan(l: Long) {

    }

    var lastIdGetWeatherByCityBlocking: Int? = null

    var blockingResult = ArrayList<DBWeather>()

    var lastIdFromGetWeatherByCity: Int? = null

    val observableWeatherByCityID = SingleSubject.create<MutableList<DBWeather>>()

    var lastInsertList: MutableList<DBWeather>? = null

    override fun getWeatherByCityBlocking(cityId_: Int): MutableList<DBWeather>? {
        this.lastIdGetWeatherByCityBlocking = cityId_
        return blockingResult
    }

    override fun getWeatherByCity(cityId_: Int): Single<MutableList<DBWeather>> {
        this.lastIdFromGetWeatherByCity = cityId_
        return observableWeatherByCityID
    }

    override fun insert(weathers: MutableList<DBWeather>?) {
        this.lastInsertList = weathers
    }


}

class WeatherApiStub : WeatherApi {

    private val weather5_3 = BehaviorSubject.create<DailyWeatherResponse>()


    override fun getWeather5_3(id: Int?, appId: String?): Observable<DailyWeatherResponse> {
        return weather5_3
    }

    fun onNextWeather5_3(value: DailyWeatherResponse) {
        weather5_3.onNext(value)
    }

    fun onErrorWeather5_3(dailyWeather: Throwable) {
        weather5_3.onError(dailyWeather)
    }

}
