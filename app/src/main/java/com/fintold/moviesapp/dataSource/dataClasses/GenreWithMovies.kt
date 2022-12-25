package com.fintold.moviesapp.dataSource.dataClasses

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie

data class GenreWithMovies(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val moviesList: List<Movie>
)