package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    // having startDate and endDate as optional parameters within SQL query
    @Query(
        "SELECT * FROM Asteroid" +
                " WHERE (:startDate IS null OR closeApproachDate >= :startDate)" +
                " AND (:endDate IS null OR closeApproachDate <= :endDate)" +
                " ORDER BY closeApproachDate ASC"
    )
    fun getAsteroidList(
        startDate: String? = null,
        endDate: String? = null
    ): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroidList: List<Asteroid>)

    @Query("DELETE FROM Asteroid WHERE closeApproachDate < :date")
    fun deleteAsteroidsUntilProvidedDate(date: String)

}