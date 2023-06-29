package com.jpmorgan.jpmorganproject.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchedCity::class], version = 1)
abstract class SearchedCityDatabase : RoomDatabase() {
    abstract fun searchedCityDao() : SearchedCityDao
}