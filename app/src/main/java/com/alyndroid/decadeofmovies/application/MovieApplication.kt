package com.alyndroid.decadeofmovies.application

import android.app.Application
import com.alyndroid.decadeofmovies.data.local.MovieRoomDatabase
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {
}