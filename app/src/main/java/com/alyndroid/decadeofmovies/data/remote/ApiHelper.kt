package com.alyndroid.decadeofmovies.data.remote

import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse

interface ApiHelper {
    suspend fun getMovieImage(query: String): ImageWrapperResponse

}