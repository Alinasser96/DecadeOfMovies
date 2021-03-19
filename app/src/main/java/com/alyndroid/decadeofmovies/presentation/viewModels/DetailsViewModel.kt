package com.alyndroid.decadeofmovies.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alyndroid.decadeofmovies.domain.usecase.GetMovieImagesUseCase
import com.alyndroid.decadeofmovies.presentation.states.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieImagesUseCase: GetMovieImagesUseCase
) : ViewModel() {

    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Empty)
    val detailsUiState: StateFlow<DetailsUiState>
        get() = _detailsUiState


    fun search(query: String) = viewModelScope.launch {
        _detailsUiState.value = DetailsUiState.Loading
        try {
            _detailsUiState.value = DetailsUiState.Success(getMovieImagesUseCase.invoke(query))
        } catch (e: Exception) {
            _detailsUiState.value = DetailsUiState.Error(e.message.toString())
        }
    }
}

