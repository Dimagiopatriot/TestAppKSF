package com.example.testappksf.domain

import android.content.Context
import android.content.res.AssetManager
import com.example.testappksf.domain.data.Place
import java.io.IOException
import java.io.InputStream
import java.util.*


fun Context.getProperty(key: String): String =
    try {
        val properties = Properties()
        val assetManager: AssetManager = this.assets
        val inputStream: InputStream = assetManager.open("app-config.properties")
        properties.load(inputStream)
        properties.getProperty(key)
    } catch (e: IOException) {
        ""
    }

const val EMPTY = ""
const val ZERO = 0.0
val PLACE_STUB = Place(
    placeId = "some id",
    "Safeway",
    "730 Taraval Street, San Francisco, CA 94116, United States of America",
    "+1 415 682-8822",
    "https://local.safeway.com/ca/san-francisco-909.html",
    "Mon. 07:00-22:00"
)