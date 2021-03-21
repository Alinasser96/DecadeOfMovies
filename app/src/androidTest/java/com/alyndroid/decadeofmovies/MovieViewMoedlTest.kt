package com.alyndroid.decadeofmovies

import android.content.Context
import com.alyndroid.decadeofmovies.domain.usecase.GetMovieUseCaseImpl
import com.alyndroid.decadeofmovies.domain.usecase.InsertMovieUseCaseImpl
import com.alyndroid.decadeofmovies.domain.usecase.SearchMovieUseCaseImpl
import com.alyndroid.decadeofmovies.presentation.activities.MainActivity
import com.alyndroid.decadeofmovies.presentation.viewModels.MovieViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

@HiltAndroidTest
class MovieViewMoedlTest {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var searchMovieUseCaseImpl: SearchMovieUseCaseImpl

    @Inject
    lateinit var getMovieUseCaseImpl: GetMovieUseCaseImpl

    @Inject
    lateinit var insertMovieUseCaseImpl: InsertMovieUseCaseImpl


    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Test
    fun whenSearchWithText_mustSuccess() { GlobalScope.launch(Dispatchers.Main){
        val viewModel = MovieViewModel(
            searchMovieUseCaseImpl,
            insertMovieUseCaseImpl,
            getMovieUseCaseImpl,
            null
        )
        viewModel.allMovies.observeForever {  }
        assertNotNull(viewModel.allMovies)
    }}

}