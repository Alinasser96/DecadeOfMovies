package com.alyndroid.decadeofmovies

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alyndroid.decadeofmovies.data.local.MovieDao
import com.alyndroid.decadeofmovies.data.local.MovieRoomDatabase
import com.alyndroid.decadeofmovies.domain.model.Movie
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao()
    }
    @Test
    @Throws(Exception::class)
    fun insertAndGetMovie() = runBlocking {
        val movie = Movie(listOf("a","b"), listOf("a","b"), 5, "cast away", 2019)
        movieDao.insert(movie)
        val allWords = movieDao.getAllMovies().first()
        assertEquals(allWords[0].title, movie.title)
    }

    @Test
    @Throws(Exception::class)
    fun insertAllAndGetAllMovies() = runBlocking {
        val movies = listOf(Movie(listOf("a","b"), listOf("a","b"), 5, "cast away", 2019),
            Movie(listOf("aaa","bbb"), listOf("aa","bb"), 4, "cast away||", 2020))
        movieDao.insertAll(movies)
        val allWords = movieDao.getAllMoviesList()
        assertEquals(allWords, movies)
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}