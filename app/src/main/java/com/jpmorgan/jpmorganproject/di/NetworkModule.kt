package com.jpmorgan.jpmorganproject.di

import com.jpmorgan.jpmorganproject.network.service.CityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class city

    @Provides
    @Singleton
    @city
    fun provideCityRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @city
    fun provideCityService(
        @city retrofit: Retrofit
    ) : CityService {
        return retrofit.create(CityService::class.java)
    }
}