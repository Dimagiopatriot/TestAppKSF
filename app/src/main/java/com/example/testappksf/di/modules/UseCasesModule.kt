package com.example.testappksf.di.modules

import com.example.testappksf.domain.usecases.GetPlacesApiResponseUseCase
import com.example.testappksf.domain.usecases.MapResponseToPlacesUseCase
import com.example.testappksf.repositories.nerwork.PlacesRepository
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun provideGetFoodAndDrinksResponseUseCase(placesRepository: PlacesRepository) =
        GetPlacesApiResponseUseCase(placesRepository)

    @Provides
    fun provideMapResponseToPlacesUseCase() = MapResponseToPlacesUseCase()
}