package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse

interface GetMovieImagesUseCase {
    suspend operator fun invoke(query: String): ImageWrapperResponse
}