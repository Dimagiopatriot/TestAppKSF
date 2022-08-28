package com.example.testappksf.repositories.nerwork

import com.example.testappksf.repositories.nerwork.response.Feature

class PlacesRepositoryImpl(
    private val restApiManager: RestApiManager
) : PlacesRepository {

    private val placesApi: PlacesAPI by lazy {
        restApiManager.buildFromURL()
    }

    override suspend fun getPlacesByCoordinates(
        categories: String,
        lat: Double,
        lon: Double,
        offset: Int
    ): List<Feature> {
        val limit = restApiManager.getPlacesLimit()
        val apiKey = restApiManager.getApiKey()
        val response = placesApi.getPlaces(
            bias = "proximity:$lon,$lat",
            offset = offset,
            categories = categories,
            apiKey = apiKey,
            limit = limit.toInt()
        )
        return if (response.body() != null && response.isSuccessful) {
            response.body()!!.features
        } else {
            listOf()
        }
    }
}