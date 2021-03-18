package com.alyndroid.coroutinestutorialstest

import com.alyndroid.decadeofmovies.domain.model.ImageWrapperResponse
import com.alyndroid.decadeofmovies.util.FLICKER_API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @GET("/services/rest/?method=flickr.photos.search")
    suspend fun queryMovie(
        @Query("api_key") apiKey: String = FLICKER_API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") callbackNum: Int = 1,
        @Query("text") title: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): ImageWrapperResponse
}