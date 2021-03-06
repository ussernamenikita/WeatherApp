package com.nikita.bulygin.weatherapp.domain;


import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IWeatherRepository {


    /**
     * Return list of weather for specific city
     * @param city weather required city
     * @return list of weather
     */
    Single<DomainResponse<List<Weather>>> getWeatherFromCity(City city);
}
