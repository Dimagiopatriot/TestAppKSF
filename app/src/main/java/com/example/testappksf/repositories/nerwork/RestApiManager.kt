package com.example.testappksf.repositories.nerwork

import android.content.Context
import com.example.testappksf.domain.getProperty
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class RestApiManager @Inject constructor(
    private val moshi: Moshi,
    private val okHttpClient: OkHttpClient,
    private val context: Context
) {
    private val converterFactory by lazy {
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    fun buildFromURL(): PlacesAPI {
        val url = context.getProperty("api_url")
        return converterFactory
            .baseUrl(url)
            .build().create(PlacesAPI::class.java)
    }

    fun getPlacesLimit() = context.getProperty("places_limit")

    fun getApiKey() = context.getProperty("api_key")
}