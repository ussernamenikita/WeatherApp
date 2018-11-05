package com.nikita.bulygin.weatherapp.domain

import com.nikita.bulygin.weatherapp.domain.entities.Weather
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


class WeatherInteractorImplTest {

    private lateinit var interactorImpl: WeatherInteractorImpl
    private val cityRepo = CityRepositoryStub()
    private val weatherRepo = WeatherRepositoryStub()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        interactorImpl = WeatherInteractorImpl(weatherRepo, cityRepo)
    }

    @Test
    @Throws(Exception::class)
    fun filterByDays() {
        val w1_1 = Weather(Date(0), 20.0)
        //+5 hour same day
        val w1_2 = Weather(Date(5 * 3600000), 100.0)
        //+10 hour same day
        val w1_3 = Weather(Date(10 * 3600000), 20.0)
        //other day
        val nextDay_1 = Weather(Date(24 * 3600 * 1000 + 1), 50.0)
        val nextDay_2 = Weather(Date(24 * 3600 * 1000 + 3 * 3600 * 1000), 60.0)
        //3th day
        val w3_1 = Weather(Date(24 * 2 * 3600 * 1000 + 1), 100.0)
        val w3_2 = Weather(Date(24 * 2 * 3600 * 1000 + 3 * 3600 * 1000), 80.0)
        val response = DomainResponse(listOf(w1_1, w3_2, nextDay_1, w1_2, nextDay_2, w1_3, w3_1),ErrorCodes.NO_ERROR,DomainResponse.RESULT_STATUS.SUCCESS)
        val result = interactorImpl.groupByDays(response)
        val weather1Result = Weather(Date(0),70.0)
        val weather2Result = Weather(Date(24 * 3600 * 1000 ),55.0)
        val weather3Result = Weather(Date(24 * 2 * 3600 * 1000 ),90.0)
        assertEquals(3,result.data!!.size)
        result.data!!.containsAll(listOf(weather1Result,weather2Result,weather3Result))
    }


}