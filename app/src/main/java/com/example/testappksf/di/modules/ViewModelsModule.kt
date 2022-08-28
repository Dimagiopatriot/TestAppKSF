package com.example.testappksf.di.modules

import com.example.testappksf.domain.usecases.GetPlacesApiResponseUseCase
import com.example.testappksf.domain.usecases.MapResponseToPlacesUseCase
import com.example.testappksf.domain.viewmodels.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun provideMainActivityViewModel(
        getPlacesApiResponseUseCase: GetPlacesApiResponseUseCase,
        mapResponseUseCase: MapResponseToPlacesUseCase
    ) = MainActivityViewModel(getPlacesApiResponseUseCase, mapResponseUseCase)
}