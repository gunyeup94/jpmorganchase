package com.jpmorgan.jpmorganproject.network.service

import com.jpmorgan.jpmorganproject.BuildConfig
import com.jpmorgan.jpmorganproject.network.response.City
import com.jpmorgan.jpmorganproject.network.response.WeatherRo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {
    @GET("geo/1.0/direct")
    suspend fun getCityInfoByName(
        @Query("q") q : String,
        @Query("limit") limit : Int,
        @Query("appid") appid : String = BuildConfig.API_KEY
    ) : Response<List<City>>

    @GET("geo/1.0/reverse")
    suspend fun getCityInfoByLatLon(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("limit") limit: Int,
        @Query("appid") appid : String = BuildConfig.API_KEY
    ) : Response<List<City>>

    @GET("data/2.5/weather")
    suspend fun getWeatherByLonLat(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("units") units : String,
        @Query("appid") appid : String = BuildConfig.API_KEY
    ) : Response<WeatherRo>
}