package com.fintold.moviesapp.dataSource.dataClasses

import androidx.room.Entity

@Entity(tableName = "movie_genre_table", primaryKeys = ["movieId","genreId"])
data class MovieGenreCrossRef (
    val movieId: Long,
    val genreId: Int
)
