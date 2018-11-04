package com.nikita.bulygin.weatherapp.data.mappers

import com.nikita.bulygin.weatherapp.data.db.DBCity
import com.nikita.bulygin.weatherapp.domain.entities.City

/**
 * Mapper for City and DBCity
 */
object CityMapper {

    @JvmStatic
    fun fromDBCity(city: DBCity): City {
        return City(city.id, city.name, city.country)
    }


    @JvmStatic
    fun toDBCity(city: City): DBCity {
        return DBCity(city.id, city.name, city.countryCode)
    }


    @JvmStatic
    fun toDBCity(city: com.nikita.bulygin.weatherapp.data.network.pojo.City): DBCity {
        return DBCity(city.id, city.name, city.country)
    }

    @JvmStatic
    fun fromArrayDBCities(array: List<DBCity>): List<City> {
        val resultArry = ArrayList<City>(array.size)
        for (city in array) {
            resultArry.add(fromDBCity(city))
        }
        return resultArry
    }

    @JvmStatic
    fun toArrayDBCities(array: List<City>): List<DBCity> {
        val resultArry = ArrayList<DBCity>(array.size)
        for (city in array) {
            resultArry.add(toDBCity(city))
        }
        return resultArry
    }

    @JvmStatic
    fun toArrayDBCitiesFromPojo(array: List<com.nikita.bulygin.weatherapp.data.network.pojo.City>): List<DBCity> {
        val resultArry = ArrayList<DBCity>(array.size)
        for (city in array) {
            resultArry.add(toDBCity(city))
        }
        return resultArry
    }

}
