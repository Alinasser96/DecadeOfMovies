package com.alyndroid.decadeofmovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alyndroid.decadeofmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    fun getAllMoviesList(): List<Movie>

    @Query("""
  SELECT *
  FROM movie_table
  JOIN movies_fts ON movie_table.title = movies_fts.title
  WHERE movies_fts MATCH :query
""")
    suspend fun search(query: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}