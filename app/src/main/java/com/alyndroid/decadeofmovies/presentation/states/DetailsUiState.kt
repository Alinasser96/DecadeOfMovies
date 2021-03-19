package com.alyndroid.decadeofmovies.presentation.states

import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse

sealed class DetailsUiState {
    data class Success(val imageWrapperResponse: ImageWrapperResponse): DetailsUiState()
    data class Error(val errorMessage: String): DetailsUiState()
    object Loading: DetailsUiState()
    object Empty: DetailsUiState()
}