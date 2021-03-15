package com.alyndroid.decadeofmovies.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    @PrimaryKey @ColumnInfo(name = "title")
    val title: String,
    val year: Int
)