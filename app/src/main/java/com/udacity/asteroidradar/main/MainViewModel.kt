package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.model.AsteroidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val asteroidRepository: AsteroidRepository
) : ViewModel() {

    val asteroidList = asteroidRepository.getAsteroidList()

    fun refreshAsteroidData() {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroidList()
        }
    }
}