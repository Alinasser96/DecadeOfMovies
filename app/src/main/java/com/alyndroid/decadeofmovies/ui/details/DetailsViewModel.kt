package com.alyndroid.decadeofmovies.ui.details

import androidx.lifecycle.*
import com.alyndroid.coroutinestutorialstest.ApiService
import com.alyndroid.decadeofmovies.pojo.ImageWrapperResponse
import kotlinx.coroutines.launch

class DetailsViewModel() : ViewModel() {

    private val _searchResults = MutableLiveData<ImageWrapperResponse>()
    val searchResults: LiveData<ImageWrapperResponse>
        get() = _searchResults

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading



    fun search(query: String) = viewModelScope.launch {
        _loading.value = true
        _searchResults.value  = ApiService.apiService.queryMovie(title = query)
        _loading.value = false
    }
}

