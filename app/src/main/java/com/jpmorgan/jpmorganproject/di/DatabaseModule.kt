package com.jpmorgan.jpmorganproject.di

import android.content.Context
import androidx.room.Room
import com.jpmorgan.jpmorganproject.MyApplication
import com.jpmorgan.jpmorganproject.room.SearchedCityDao
import com.jpmorgan.jpmorganproject.room.SearchedCityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext application: Context) : SearchedCityDatabase {
        return Room.databaseBuilder(
            application,
            SearchedCityDatabase::class.java,
            "searched-city"
        ).build()
    }

    @Provides
    fun provideSearchedCityDao(database: SearchedCityDatabase) : SearchedCityDao {
        return database.searchedCityDao()
    }
}