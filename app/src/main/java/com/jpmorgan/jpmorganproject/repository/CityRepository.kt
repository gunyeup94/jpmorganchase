package com.jpmorgan.jpmorganproject.repository

import com.jpmorgan.jpmorganproject.network.response.City
import com.jpmorgan.jpmorganproject.network.response.WeatherRo
import com.jpmorgan.jpmorganproject.network.service.CityService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository
@Inject
constructor(
    private val service : CityService
) {
    suspend fun getCityInfoByName(
        q : String, limit : Int
    ) : Response<List<City>> {
        return service.getCityInfoByName(q,limit)
    }

    suspend fun getWeatherByLonLat(
        lat : Double, lon : Double, units : String
    ) : Response<WeatherRo> {
        return service.getWeatherByLonLat(lat, lon, units)
    }

}