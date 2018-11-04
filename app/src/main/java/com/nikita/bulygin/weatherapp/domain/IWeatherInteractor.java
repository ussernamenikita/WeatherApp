package com.nikita.bulygin.weatherapp.domain;

import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;

import java.util.List;

import io.reactivex.Single;

/**
 * Class for work with weather
 */

public interface IWeatherInteractor {

    /**
     * Returns weather in {@code city} for 5 day
     *
     * @param city what city is the weather for
     * @return list of the weather
     */
    Single<DomainResponse<List<Weather>>> getWeather(City city);

    /**
     * Returns cities with specified prefix
     *
     * @param prefix city prefix
     * @return list of city which name begins from {@code prefix}
     */
    Single<DomainResponse<List<City>>> getCitiesForPrefix(String prefix);
}
