package com.udacity.asteroidradar.di

import android.content.Context
import androidx.room.Room
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.database.AsteroidDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideAsteroidDatabase(@ApplicationContext applicationContext: Context): AsteroidDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AsteroidDatabase::class.java,
            "asteroid_database"
        ).build()
    }


    @Singleton
    @Provides
    fun provideAsteroidApiService(): AsteroidApiService {

        // to enable changing default timeout
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()

        return retrofit.create(AsteroidApiService::class.java)
    }
}