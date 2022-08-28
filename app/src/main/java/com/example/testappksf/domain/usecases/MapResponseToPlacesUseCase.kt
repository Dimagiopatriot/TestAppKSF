package com.example.testappksf.domain.usecases

import com.example.testappksf.domain.EMPTY
import com.example.testappksf.domain.data.Place
import com.example.testappksf.repositories.nerwork.response.Feature

class MapResponseToPlacesUseCase {

    fun mapResponse(response: List<Feature>): List<Place> {
        return response.map {
            Place(
                placeId = it.properties.placeId,
                placeName = it.properties.name
                    ?: if (it.properties.datasource?.raw?.shop != null) {
                        it.properties.datasource.raw.shop
                    } else {
                        EMPTY
                    },
                address = it.properties.addressLineAdditional ?: EMPTY,
                phoneNumber = it.properties.datasource?.raw?.phone,
                webSite = it.properties.datasource?.raw?.website,
                openingHours = it.properties.datasource?.raw?.openingHours
            )
        }
    }
}