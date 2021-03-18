package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class InsertMovieUseCaseImpl
 @Inject constructor(private val moviesRepository: MoviesRepository): InsertMoviesUseCase {
    override suspend fun invoke(movies: List<Movie>) = moviesRepository.insertAll(movies)
}