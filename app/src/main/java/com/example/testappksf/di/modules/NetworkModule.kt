package com.example.testappksf.di.modules

import android.content.Context
import com.example.testappksf.repositories.nerwork.RestApiManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    internal fun providesHTTPClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    internal fun provideRestApiManager(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        context: Context
    ): RestApiManager = RestApiManager(moshi, okHttpClient, context)
}