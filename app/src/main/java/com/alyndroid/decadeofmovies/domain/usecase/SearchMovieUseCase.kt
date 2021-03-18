package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie


interface SearchMovieUseCase {
    suspend operator fun invoke(query: String): List<Movie>
}