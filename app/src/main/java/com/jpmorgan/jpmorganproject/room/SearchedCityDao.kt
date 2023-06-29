package com.jpmorgan.jpmorganproject.room

import androidx.room.*

@Dao
interface SearchedCityDao {
    @Insert
    suspend fun insert(searchedCity: SearchedCity)

    @Update
    suspend fun update(searchedCity: SearchedCity)

    @Delete
    suspend fun delete(searchedCity: SearchedCity)

    @Query("DELETE FROM searchedCity")
    suspend fun deleteAll()

    @Query("SELECT * FROM searchedCity")
    suspend fun getCities() : List<SearchedCity>
}