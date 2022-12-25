package com.fintold.moviesapp.dataSource.localSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

@Database(entities = [Movie::class, Genre::class, MovieGenreCrossRef::class], version = 5, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract val databaseDao: DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase?=null

        fun getInstance(context: Context) : MoviesDatabase {
            synchronized(lock = this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java, "movies_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
