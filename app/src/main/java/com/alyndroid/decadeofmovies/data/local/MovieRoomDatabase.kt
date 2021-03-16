package com.alyndroid.decadeofmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alyndroid.decadeofmovies.pojo.Movie
import com.alyndroid.decadeofmovies.pojo.MovieFTS

@Database(entities = [Movie::class, MovieFTS::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
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
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}