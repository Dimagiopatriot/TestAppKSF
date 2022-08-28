package com.example.testappksf.di.modules

import com.example.testappksf.repositories.nerwork.PlacesRepository
import com.example.testappksf.repositories.nerwork.PlacesRepositoryImpl
import com.example.testappksf.repositories.nerwork.RestApiManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providePlacesRepository(
        restApiManager: RestApiManager
    ): PlacesRepository = PlacesRepositoryImpl(restApiManager)
}