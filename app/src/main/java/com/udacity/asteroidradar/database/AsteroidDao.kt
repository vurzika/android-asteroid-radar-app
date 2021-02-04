package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :startDate ORDER BY closeApproachDate ASC")
    fun getAsteroidList(startDate: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroidList: List<Asteroid>)

}