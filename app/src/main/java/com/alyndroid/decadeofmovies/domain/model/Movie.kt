package com.alyndroid.decadeofmovies.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie_table")

@Parcelize
data class Movie(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    @PrimaryKey @ColumnInfo(name = "title")
    val title: String,
    val year: Int
): Parcelable

//make new Entity for virtual full text search table
@Entity(tableName = "movies_fts")
@Fts4(contentEntity = Movie::class)
data class MovieFTS(
    @ColumnInfo(name = "title")
    val title: String
)