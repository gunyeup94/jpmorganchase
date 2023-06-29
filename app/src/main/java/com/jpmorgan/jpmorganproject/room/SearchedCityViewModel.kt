package com.jpmorgan.jpmorganproject.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmorgan.jpmorganproject.repository.SearchedCityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ViewModel for the room database
 * '_searchedCities' is the history of the searched city
 * '_hasNoCity" is the boolean that shows if the user has been searched at least one city
 */
@HiltViewModel
class SearchedCityViewModel
@Inject
constructor(private val searchedCityRepository: SearchedCityRepository) : ViewModel(){
    private val _searchedCities : MutableLiveData<List<SearchedCity>> = MutableLiveData()

    private val _hasNoCity : MutableLiveData<Boolean> = MutableLiveData()

    val hasNoCity : LiveData<Boolean>
        get() = _hasNoCity

    val searchedCities : LiveData<List<SearchedCity>>
        get() = _searchedCities

    /**
     * retrieve searched cities
     */
    fun getCities() {
        viewModelScope.launch {
            val result = searchedCityRepository.getSearchedCities()
            if (result.isNullOrEmpty()) {
                _hasNoCity.value = true
            } else {
                _searchedCities.value = result
            }
        }
    }

    /**
     * insert searched city
     * this method is called when user clicked one of the cities from the city list
     */
    fun insertCity(city : SearchedCity) {
        viewModelScope.launch {
            searchedCityRepository.insert(city)
        }
    }
}