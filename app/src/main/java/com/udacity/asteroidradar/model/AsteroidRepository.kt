package com.udacity.asteroidradar.model

import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AsteroidRepository @Inject constructor(
    private val database: AsteroidDatabase,
    private val asteroidService: AsteroidApiService
) {

    companion object {
        private val queryDateFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
    }

    // Load asteroid list from the offline cache
    fun getAsteroidList() = database.asteroidDao().getAsteroidList(startDate = getTodayAsString())

    // Refreshing offline cache
    suspend fun refreshAsteroidList() = withContext(Dispatchers.IO) {
        // API_QUERY_DATE_FORMAT
        val asteroidString = asteroidService.getAsteroidProperties(startDate = getTodayAsString())
        val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidString))
        database.asteroidDao().insertAll(asteroidList)
    }

    private fun getTodayAsString() = queryDateFormatter.format(LocalDate.now())
}