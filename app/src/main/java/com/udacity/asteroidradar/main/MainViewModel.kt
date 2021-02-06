package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val asteroidList = asteroidRepository.getAsteroidList()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    fun refreshAsteroidData() {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroidList()
        }
    }

    fun refreshPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDay.value = asteroidRepository.getPictureOfTheDay()
        }
    }
}