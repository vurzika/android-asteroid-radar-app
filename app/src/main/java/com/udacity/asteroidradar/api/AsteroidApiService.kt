package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidApiService {
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroidProperties(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): String
}

