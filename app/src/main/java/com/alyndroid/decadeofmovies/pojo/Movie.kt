package com.alyndroid.decadeofmovies.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
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

@Entity(tableName = "movies_fts")
@Fts4(contentEntity = Movie::class)
data class MovieFTS(
    @ColumnInfo(name = "title")
    val title: String
)