package com.alyndroid.decadeofmovies.domain.repositories

import com.alyndroid.decadeofmovies.data.remote.ApiHelper
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getMovieImage(query: String) = apiHelper.getMovieImage(query)
}
