package com.alyndroid.coroutinestutorialstest

import com.alyndroid.decadeofmovies.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val reteofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object ApiService {
    val apiService: ApiInterface by lazy {
        reteofit.create(ApiInterface::class.java)
    }
}