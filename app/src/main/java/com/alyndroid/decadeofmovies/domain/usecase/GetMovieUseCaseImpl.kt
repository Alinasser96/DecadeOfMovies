package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCaseImpl
 @Inject constructor(private val moviesRepository: MoviesRepository): GetMovieUseCase {
    override fun invoke(): Flow<List<Movie>> = moviesRepository.allMovies
}