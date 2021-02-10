package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApiService {

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): PictureOfDay
}