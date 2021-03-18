package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieUseCase {
    operator fun invoke() : Flow<List<Movie>>
}