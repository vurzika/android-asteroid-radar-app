package com.udacity.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.model.Asteroid

class DetailViewModel(asteroid: Asteroid) : ViewModel() {

    val selectedAsteroid: LiveData<Asteroid> = MutableLiveData(asteroid)

}