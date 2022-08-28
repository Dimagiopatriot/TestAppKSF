package com.example.testappksf.repositories.nerwork

import com.example.testappksf.repositories.nerwork.response.Feature

interface PlacesRepository {

    suspend fun getPlacesByCoordinates(categories: String, lat: Double, lon: Double, offset: Int): List<Feature>

}