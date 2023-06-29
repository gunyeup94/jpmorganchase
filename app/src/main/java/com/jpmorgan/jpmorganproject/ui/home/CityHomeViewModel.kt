package com.jpmorgan.jpmorganproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmorgan.jpmorganproject.network.response.City
import com.jpmorgan.jpmorganproject.network.response.WeatherRo
import com.jpmorgan.jpmorganproject.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * ...ViewModel for weather app api
 * ...'_cityList' is for searching by the name
 * ...'_weatherData' is for weather info when user select city from the city list
 */
@HiltViewModel
class CityHomeViewModel
@Inject
constructor(
    private val cityRepository: CityRepository
) : ViewModel() {
    private val _cityList : MutableLiveData<List<City>> = MutableLiveData()

    private val _weatherData : MutableLiveData<WeatherRo> = MutableLiveData()

    val weatherData : LiveData<WeatherRo>
        get() = _weatherData

    val cityList : LiveData<List<City>>
        get() = _cityList

    /**
     * Requesting api by city name to get corresponding cities
     * q : String -> string that is searched by user
     * limit : Int -> number of the max list size
     */
    fun getCityInfoByName(q : String, limit : Int) {
        try {
            viewModelScope.launch {
                val result = cityRepository.getCityInfoByName(q, limit)
                if (result.isSuccessful) {
                    result.body()?.let {
                        _cityList.value = it
                    }
                } else {
                    println(result.message())
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Requesting weather api to get city's weather information
     * lat : Double -> latitude
     * lon : Double -> longitude
     */
    fun getWeatherByLonLat(lat : Double, lon : Double, units : String) {
        try {
            viewModelScope.launch {
                val result = cityRepository.getWeatherByLonLat(lat, lon, units)
                if (result.isSuccessful) {
                    result.body()?.let {
                        _weatherData.value = it
                    }
                } else {
                    println(result.message())
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}
