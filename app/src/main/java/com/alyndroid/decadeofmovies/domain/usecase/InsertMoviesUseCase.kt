package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie

interface InsertMoviesUseCase {
    suspend operator fun invoke(movies: List<Movie>)
}