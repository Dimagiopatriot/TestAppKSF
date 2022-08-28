package com.example.testappksf.domain.data

import com.example.testappksf.domain.EMPTY

data class Place(
    val placeId: String,
    val placeName: String,
    val address: String,
    val phoneNumber: String? = EMPTY,
    val webSite: String? = EMPTY,
    val openingHours: String? = EMPTY
)