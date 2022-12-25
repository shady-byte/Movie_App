package com.fintold.moviesapp.dataSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Genres_table")
data class Genre(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id") val genreId: Int,

    val name: String,
)

data class ResultOfGenresApi(
    val genres: List<Genre>
)
