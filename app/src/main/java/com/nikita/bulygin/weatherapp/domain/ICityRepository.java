package com.nikita.bulygin.weatherapp.domain;


import com.nikita.bulygin.weatherapp.domain.entities.City;

import java.util.List;

import io.reactivex.Single;

public interface ICityRepository {

    /**
     * Returns list of cities which name begins from {@code prefix}
     *
     * @param prefix prefix of city's name
     * @return list of cities which names begins from {@code prefix}
     */
    Single<DomainResponse<List<City>>> getPossibleCities(String prefix);
}
