package com.jpmorgan.jpmorganproject.di

import androidx.room.Database
import com.jpmorgan.jpmorganproject.network.service.CityService
import com.jpmorgan.jpmorganproject.repository.CityRepository
import com.jpmorgan.jpmorganproject.repository.SearchedCityRepository
import com.jpmorgan.jpmorganproject.room.SearchedCityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        @NetworkModule.city service: CityService
    ) : CityRepository {
        return CityRepository(service)
    }
}