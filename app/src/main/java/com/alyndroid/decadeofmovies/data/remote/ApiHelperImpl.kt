package com.alyndroid.decadeofmovies.data.remote

import com.alyndroid.coroutinestutorialstest.ApiInterface
import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiInterface: ApiInterface
):ApiHelper{
    override suspend fun getMovieImage(query: String): ImageWrapperResponse  = apiInterface.queryMovie(title= query)
}