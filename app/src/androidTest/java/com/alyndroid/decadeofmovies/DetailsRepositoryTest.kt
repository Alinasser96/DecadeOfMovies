package com.alyndroid.decadeofmovies

import com.alyndroid.decadeofmovies.domain.repositories.DetailsRepository
import com.alyndroid.decadeofmovies.domain.usecase.GetMovieImagesUseCaseImpl
import com.alyndroid.decadeofmovies.presentation.viewModels.DetailsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DetailsRepositoryTest {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var base: String

    @Inject
    lateinit var repository: DetailsRepository





    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Test
    fun whenSearchWithText_mustSuccess() = runBlocking {
        val imageWrapperResponse = repository.getMovieImage("?")
        assertEquals("ok", imageWrapperResponse.stat)
    }

    @Test
    fun whenSearchWithEmptyText_mustFail() = runBlocking {
        val imageWrapperResponse = repository.getMovieImage("")
        assertEquals("fail", imageWrapperResponse.stat)
    }
}