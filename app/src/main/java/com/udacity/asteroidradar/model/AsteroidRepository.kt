package com.udacity.asteroidradar.model

import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.api.PictureOfTheDayApiService
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
    private val asteroidService: AsteroidApiService,
    private val pictureOfTheDayService: PictureOfTheDayApiService
) {

    companion object {
        private val queryDateFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(API_QUERY_DATE_FORMAT)
    }

    // Load asteroid list from the offline cache
    fun getTodayAsteroidList() = getAsteroidList(
        startDate = getTodayAsString(),
        endDate = getTodayAsString()
    )

    fun getWeeklyAsteroidList() = getAsteroidList(
        startDate = getTodayAsString(),
        endDate = getGetTodayPlusSixDaysAsString()
    )

    fun getSavedAsteroidList() = getAsteroidList()

    // Common method for retrieving dates
    private fun getAsteroidList(startDate: String? = null, endDate: String? = null) =
        database.asteroidDao().getAsteroidList(startDate, endDate)


    // refresh list of asteroids from network

    suspend fun refreshTodayAsteroidList() = refreshAsteroidList(
        startDate = getTodayAsString(),
        endDate = getTodayAsString()
    )

    suspend fun refreshWeeklyAsteroidList() = refreshAsteroidList(
        startDate = getTodayAsString(),
        endDate = getGetTodayPlusSixDaysAsString()
    )

    private suspend fun refreshAsteroidList(startDate: String? = null, endDate: String? = null) =
        withContext(Dispatchers.IO) {
            val asteroidString = asteroidService.getAsteroidProperties(startDate, endDate)
            val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidString))
            database.asteroidDao().insertAll(asteroidList)
        }

    // returns today's date formatted as string
    private fun getTodayAsString() = queryDateFormatter.format(LocalDate.now())

    // returns today + 6 days formatted as string
    private fun getGetTodayPlusSixDaysAsString() =
        queryDateFormatter.format(LocalDate.now().plusDays(6))

    // Picture of the Day

    suspend fun getPictureOfTheDay() = withContext(Dispatchers.IO) {
        pictureOfTheDayService.getPictureOfTheDay()
    }
}