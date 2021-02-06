package com.udacity.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.model.Asteroid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {


    // getting navigation argument as LiveData from navigation arguments
    val selectedAsteroid: LiveData<Asteroid> = state.getLiveData("selectedAsteroid")
}