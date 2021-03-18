package com.alyndroid.decadeofmovies.domain.di.modules

import com.alyndroid.decadeofmovies.domain.repositories.DetailsRepository
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import com.alyndroid.decadeofmovies.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchUseCase(moviesRepository: MoviesRepository): SearchMovieUseCase =
        SearchMovieUseCaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideInsertUseCase(moviesRepository: MoviesRepository): InsertMoviesUseCase =
        InsertMovieUseCaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideGetUseCase(moviesRepository: MoviesRepository): GetMovieUseCase =
        GetMovieUseCaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideGetImagesUseCase(detailsRepository: DetailsRepository): GetMovieImagesUseCase =
        GetMovieImagesUseCaseImpl(detailsRepository)
}