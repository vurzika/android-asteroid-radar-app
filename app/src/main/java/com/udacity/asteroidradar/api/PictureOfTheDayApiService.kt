package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApiService {

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = Constants.API_KEY_VALUE
    ): PictureOfDay
}