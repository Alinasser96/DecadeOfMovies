package com.alyndroid.decadeofmovies.util

import android.content.Context
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun jsonToPojo(jsonFileName: String, context: Context): List<Movie> {
    val jsonFileString = getJsonDataFromAsset(context, jsonFileName)
    val gson = Gson()
    val listPersonType = object : TypeToken<List<Movie>>() {}.type
    return gson.fromJson(jsonFileString, listPersonType)
}

