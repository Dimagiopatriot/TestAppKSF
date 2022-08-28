package com.example.testappksf.repositories.nerwork.response

import com.squareup.moshi.Json

data class ApiResponse(
    val features: List<Feature>
)

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val name: String? = null,
    @Json(name = "housenumber")
    val houseNumber: String? = null,
    val street: String? = null,
    val suburb: String? = null,
    val city: String? = null,
    val state: String? = null,
    val postcode: String? = null,
    val country: String? = null,
    @Json(name = "country_code")
    val countryCode: String? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val formatted: String? = null,
    @Json(name = "address_line1")
    val addressLine: String? = null,
    @Json(name = "address_line2")
    val addressLineAdditional: String? = null,
    val datasource: Datasource? = null,
    @Json(name = "place_id")
    val placeId: String
)

data class Datasource(
    @Json(name = "sourcename")
    val sourceName: String? = null,
    val attribution: String? = null,
    val license: String? = null,
    val url: String? = null,
    val raw: RawInfo? = null
)

data class RawInfo(
    val name: String? = null,
    val shop: String? = null,
    val phone: String? = null,
    val website: String? = null,
    @Json(name = "opening_hours")
    val openingHours: String? = null
)

data class Geometry(val type: String, val coordinates: List<Double>)