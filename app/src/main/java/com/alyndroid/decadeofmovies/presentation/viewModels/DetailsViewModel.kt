package com.alyndroid.decadeofmovies.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse
import com.alyndroid.decadeofmovies.domain.usecase.GetMovieImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieImagesUseCase: GetMovieImagesUseCase
) : ViewModel() {

    private val _searchResults = MutableLiveData<ImageWrapperResponse>()
    val searchResults: LiveData<ImageWrapperResponse>
        get() = _searchResults

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun search(query: String) = viewModelScope.launch {
        _loading.value = true
        try {
            _searchResults.value = getMovieImagesUseCase.invoke(query)
        } catch (e: Exception){

        }
        _loading.value = false
    }
}

