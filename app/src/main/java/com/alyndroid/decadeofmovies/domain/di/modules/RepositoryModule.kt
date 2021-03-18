package com.alyndroid.decadeofmovies.domain.di.modules

import com.alyndroid.decadeofmovies.data.local.MovieDao
import com.alyndroid.decadeofmovies.data.remote.ApiHelper
import com.alyndroid.decadeofmovies.domain.repositories.DetailsRepository
import com.alyndroid.decadeofmovies.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(localDataSource: MovieDao) =
        MoviesRepository(localDataSource)

    @Singleton
    @Provides
    fun provideDetailsRepository(apiHelper: ApiHelper) =
        DetailsRepository(apiHelper)
}