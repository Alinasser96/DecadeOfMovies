package com.alyndroid.decadeofmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alyndroid.decadeofmovies.domain.model.Movie
import com.alyndroid.decadeofmovies.domain.model.MovieFTS

@Database(entities = [Movie::class, MovieFTS::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}