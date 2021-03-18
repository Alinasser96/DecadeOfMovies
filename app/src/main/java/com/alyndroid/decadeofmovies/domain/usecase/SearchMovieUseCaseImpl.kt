package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCaseImpl
 @Inject constructor(private val moviesRepository: MoviesRepository): SearchMovieUseCase {
    override suspend fun invoke(query: String): List<Movie> = moviesRepository.search(query)
}