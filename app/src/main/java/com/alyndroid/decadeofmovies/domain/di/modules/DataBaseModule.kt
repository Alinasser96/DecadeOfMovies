package com.alyndroid.decadeofmovies.domain.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alyndroid.decadeofmovies.data.local.MovieDao
import com.alyndroid.decadeofmovies.data.local.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Provides
    fun provideChannelDao(movieDatabase: MovieRoomDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieRoomDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            MovieRoomDatabase::class.java,
            "movie_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // 3
                db.execSQL("INSERT INTO movies_fts(movies_fts) VALUES ('rebuild')")
            }
        })
            .build()
    }


}