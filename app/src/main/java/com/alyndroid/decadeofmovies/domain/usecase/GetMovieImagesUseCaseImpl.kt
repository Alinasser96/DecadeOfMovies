package com.alyndroid.decadeofmovies.domain.usecase

import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse
import com.alyndroid.decadeofmovies.domain.repositories.DetailsRepository
import javax.inject.Inject

class GetMovieImagesUseCaseImpl
@Inject constructor(private val detailsRepository: DetailsRepository) : GetMovieImagesUseCase {
    override suspend fun invoke(query: String): ImageWrapperResponse =
        detailsRepository.getMovieImage(query)
}