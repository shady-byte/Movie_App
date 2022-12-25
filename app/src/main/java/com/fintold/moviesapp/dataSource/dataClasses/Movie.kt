package com.fintold.moviesapp.dataSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "movies_table")
data class Movie(
    var imageUrl: String,

    val name: String,

    val productionDate: String,

    val rating: Double,

    val description: String,

    @PrimaryKey(autoGenerate = false)
    val movieId: Long,
)

data class RemoteMovie(
    @Json(name = "poster_path") var imageUrl: String,

    @Json(name = "title") val name: String,

    @Json(name = "release_date") val productionDate: String,

    @Json(name = "vote_average") val rating: Double,

    @Json(name = "overview") val description: String,

    @Json(name = "genre_ids") val genre: List<Int>,

    @Json(name = "id") val movieId: Long,
)

data class ResultOfMoviesApi(
    val page: Int,
    val results: List<RemoteMovie>
)
