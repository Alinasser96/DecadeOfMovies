package com.alyndroid.decadeofmovies.application

import android.app.Application
import com.alyndroid.decadeofmovies.data.local.MovieRoomDatabase
import com.alyndroid.decadeofmovies.repositories.MoviesRepository

class MovieApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MovieRoomDatabase.getDatabase(this) }
    val repository by lazy { MoviesRepository(database.movieDao()) }
}