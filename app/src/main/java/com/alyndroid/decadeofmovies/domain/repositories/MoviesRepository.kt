package com.alyndroid.decadeofmovies.domain.repositories

import androidx.annotation.WorkerThread
import com.alyndroid.decadeofmovies.data.local.MovieDao
import com.alyndroid.decadeofmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesRepository @Inject constructor(private val movieDao: MovieDao) {

    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies()

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