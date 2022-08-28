package com.example.testappksf

import com.example.testappksf.domain.data.Place
import com.example.testappksf.repositories.nerwork.response.ApiResponse
import com.example.testappksf.repositories.nerwork.response.Feature
import com.example.testappksf.repositories.nerwork.response.Geometry
import com.example.testappksf.repositories.nerwork.response.Properties

val SOME_TEXT = "some text"
val SOME_SHOP_NAME = "shop name"
val SOME_ADDRESS = "some address"
val SOME_INT = 0
val SOME_DOUBLE = 0.0

val SOME_PROPERTY = Properties(placeId = "some place id")
val SOME_GEOMETRY = Geometry(type = "some geometry type", coordinates = listOf())
val SOME_FEATURE = Feature(type = "some type", properties = SOME_PROPERTY, SOME_GEOMETRY)
val SOME_FEATURE_LIST = listOf(SOME_FEATURE)
val SOME_API_RESPONSE = ApiResponse(SOME_FEATURE_LIST)

val SOME_PLACE = Place(SOME_TEXT, SOME_TEXT, SOME_TEXT)
val SOME_PLACE_LIST = listOf(SOME_PLACE)