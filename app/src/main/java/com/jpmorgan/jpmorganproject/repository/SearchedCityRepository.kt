package com.jpmorgan.jpmorganproject.repository

import com.jpmorgan.jpmorganproject.room.SearchedCity
import com.jpmorgan.jpmorganproject.room.SearchedCityDao
import com.jpmorgan.jpmorganproject.room.SearchedCityDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchedCityRepository
@Inject
constructor(private val dao : SearchedCityDao){
    suspend fun getSearchedCities() : List<SearchedCity>{
        return dao.getCities()
    }

    suspend fun insert(searchedCity: SearchedCity) {
        dao.insert(searchedCity)
    }

    suspend fun update(searchedCity: SearchedCity) {
        dao.update(searchedCity)
    }

    suspend fun delete(searchedCity: SearchedCity) {
        dao.delete(searchedCity)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}