package com.alyndroid.decadeofmovies.repositories

import androidx.annotation.WorkerThread
import com.alyndroid.decadeofmovies.data.local.MovieDao
import com.alyndroid.decadeofmovies.pojo.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val movieDao: MovieDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(movie: List<Movie>) {
        movieDao.insertAll(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun search(query: String):List<Movie> {
        //the * is replacing the %
        return movieDao.search("*$query*")
    }
}