package com.example.testappksf.domain.usecases

import com.example.testappksf.domain.ZERO
import com.example.testappksf.repositories.nerwork.PlacesRepository
import com.example.testappksf.repositories.nerwork.response.Feature

class GetPlacesApiResponseUseCase(
    private val placesRepository: PlacesRepository
) {

    suspend fun getResponse(
        offset: Int,
        categories: String,
        lat: Double = ZERO,
        lon: Double = ZERO
    ): List<Feature> = placesRepository.getPlacesByCoordinates(categories = categories, lat, lon, offset)
}