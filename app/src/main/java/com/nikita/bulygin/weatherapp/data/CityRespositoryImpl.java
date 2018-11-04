package com.nikita.bulygin.weatherapp.data;

import com.nikita.bulygin.weatherapp.data.db.CityDao;
import com.nikita.bulygin.weatherapp.data.mappers.CityMapper;
import com.nikita.bulygin.weatherapp.domain.DomainResponse;
import com.nikita.bulygin.weatherapp.domain.ErrorCodes;
import com.nikita.bulygin.weatherapp.domain.ICityRepository;
import com.nikita.bulygin.weatherapp.domain.entities.City;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class CityRespositoryImpl implements ICityRepository {

    private CityDao cityDao;

    @Inject
    public CityRespositoryImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public Single<DomainResponse<List<City>>> getPossibleCities(String prefix) {
        return cityDao.
                getCityWithPrefix(prefix).
                map(CityMapper::fromArrayDBCities).
                map(cities -> new DomainResponse<>(cities, ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS));
    }
}
