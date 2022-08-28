package com.example.testappksf.repositories.nerwork

import com.example.testappksf.repositories.nerwork.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI {

    @GET("/v2/places")
    suspend fun getPlaces(
        @Query("bias") bias: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("categories") categories: String,
        @Query("apiKey") apiKey: String
    ): Response<ApiResponse>

}