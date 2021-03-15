package com.alyndroid.decadeofmovies.ui.main

import android.content.Context
import androidx.lifecycle.*
import com.alyndroid.decadeofmovies.helper.getJsonDataFromAsset
import com.alyndroid.decadeofmovies.pojo.Movie
import com.alyndroid.decadeofmovies.repositories.MoviesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MoviesRepository, private val context: Context) :
    ViewModel() {

    val allMovies: LiveData<List<Movie>> = repository.allMovies.asLiveData()

    private fun insertMovies(movie: List<Movie>) = viewModelScope.launch {
        repository.insertAll(movie)
    }

    fun parseJsonToRoomDB() {
        val jsonFileString = getJsonDataFromAsset(context, "movies.json")
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Movie>>() {}.type
        val movies: List<Movie> = gson.fromJson(jsonFileString, listPersonType)

        insertMovies(movies)
    }
}


class MovieViewModelFactory(
    private val repository: MoviesRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}