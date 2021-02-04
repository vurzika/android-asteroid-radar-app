package com.udacity.asteroidradar.model

import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AsteroidRepository @Inject constructor(
    private val database: AsteroidDatabase,
    private val asteroidService: AsteroidApiService
) {

    // Load asteroid list from the offline cache
    fun getAsteroidList() = database.asteroidDao().getAsteroidList()

    // Refreshing offline cache
    suspend fun refreshAsteroidList() = withContext(Dispatchers.IO) {
        val asteroidString = asteroidService.getAsteroidProperties()
        val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidString))
        database.asteroidDao().insertAll(asteroidList)
    }
}