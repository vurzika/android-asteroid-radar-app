package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.model.AsteroidRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: AsteroidRepository
) : CoroutineWorker(context, workerParameters) {

    // Work name to identify worker
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            repository.refreshAsteroidList()
            Result.success()
        } catch (error: Exception) {
            Result.failure()
        }
    }
}