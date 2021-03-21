package com.alyndroid.decadeofmovies.presentation.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.domain.usecase.GetMovieUseCase
import com.alyndroid.decadeofmovies.domain.usecase.InsertMoviesUseCase
import com.alyndroid.decadeofmovies.domain.usecase.SearchMovieUseCase
import com.alyndroid.decadeofmovies.util.jsonToPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val insertMoviesUseCase: InsertMoviesUseCase,
    getMovieUseCase: GetMovieUseCase,
    @ApplicationContext private val context: Context?
) :
    ViewModel() {

    val allMovies: LiveData<List<Movie>> = getMovieUseCase.invoke().asLiveData()

    private val _searchResults = MutableLiveData<List<Any>>()
    val searchResults: LiveData<List<Any>>
        get() = _searchResults


    fun insertJsonToRoomDB() {
        val movies = jsonToPojo("movies.json", context!!)
        viewModelScope.launch {
            insertMoviesUseCase.invoke(movies)
        }
    }

    fun search(query: String) = viewModelScope.launch {
        searchMovieUseCase.invoke(query).let { movies ->
            _searchResults.postValue(movies.asReversed()
                .groupBy { it.year } //group the result by year
                .flatMap {
                    val list = it.value.sortedByDescending { movie ->
                        movie.rating // order the result by rating **using quick sort algorithm**
                    }.take(5) //just take the first 5 items
                        .toMutableList<Any>()
                    list.add(0, it.key)
                    list
                })
        }
    }
}