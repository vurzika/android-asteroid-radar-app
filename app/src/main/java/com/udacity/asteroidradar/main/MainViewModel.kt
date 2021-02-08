package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.model.AsteroidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val asteroidRepository: AsteroidRepository
) : ViewModel() {

    enum class AsteroidsToDisplay {
        TODAY, WEEKLY, SAVED
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroidsToDisplay = MutableLiveData(AsteroidsToDisplay.WEEKLY)
    val asteroidsToDisplay: LiveData<AsteroidsToDisplay>
        get() = _asteroidsToDisplay

    // Loading indicator
    private val _loadingIndicatorVisible = MutableLiveData(false)
    val loadingIndicatorVisible: LiveData<Boolean>
        get() = _loadingIndicatorVisible

    // Automatically updating List data when Asteroids to display Changes
    val asteroidList = Transformations.switchMap(asteroidsToDisplay) { asteroidsToDisplay ->
        when (asteroidsToDisplay) {
            AsteroidsToDisplay.TODAY -> {
                viewModelScope.launch {
                    try {
                        _loadingIndicatorVisible.value = true
                        asteroidRepository.refreshTodayAsteroidList()
                    } catch (error: Exception) {

                    } finally {
                        _loadingIndicatorVisible.value = false
                    }
                }
                asteroidRepository.getTodayAsteroidList()
            }
            AsteroidsToDisplay.WEEKLY -> {
                viewModelScope.launch {
                    try {
                        _loadingIndicatorVisible.value = true
                        asteroidRepository.refreshWeeklyAsteroidList()
                    } catch (error: Exception) {

                    } finally {
                        _loadingIndicatorVisible.value = false
                    }
                }
                asteroidRepository.getWeeklyAsteroidList()
            }
            else -> asteroidRepository.getSavedAsteroidList()
        }
    }

    fun setAsteroidsToDisplay(value: AsteroidsToDisplay) {
        _asteroidsToDisplay.value = value
    }

    fun refreshPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDay.value = asteroidRepository.getPictureOfTheDay()
        }
    }
}