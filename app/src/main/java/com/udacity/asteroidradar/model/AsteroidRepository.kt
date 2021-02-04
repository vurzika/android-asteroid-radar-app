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

    suspend fun getAsteroidList() = withContext(Dispatchers.IO) {
        val asteroidString =
            asteroidService.getAsteroidProperties(startDate = "2021-02-04", endDate = "2021-02-11")
        return@withContext parseAsteroidsJsonResult(JSONObject(asteroidString))
    }


    // Loading Data manually
    // return all data
    //fun getAsteroidList(): LiveData<List<Asteroid>> = MutableLiveData(createInitialData())

    //    private fun createInitialData() = mutableListOf(
    //        Asteroid(1, "Asteroid 1", "Feb 22", 2.4, 3.6, 7.3, 5.5, true),
    //        Asteroid(2, "Asteroid 1", "Feb 22", 8.9, 3.6, 7.3, 5.5, false),
    //        Asteroid(3, "Asteroid 1", "Feb 22", 23.5, 3.6, 7.3, 5.5, true),
    //        Asteroid(4, "Asteroid 1", "Feb 22", 67.34, 3.6, 7.3, 5.5, true),
    //        Asteroid(5, "Asteroid 1", "Feb 22", 7.89, 3.6, 7.3, 5.5, true),
    //        Asteroid(6, "Asteroid 1", "Feb 22", 55.6, 3.6, 7.3, 5.5, true)
    //    )
}