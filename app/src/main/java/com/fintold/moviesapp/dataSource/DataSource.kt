package com.fintold.moviesapp.dataSource

import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

interface DataSource {
    suspend fun getGenres(): Result<List<Genre>>
}

interface RemoteSource: DataSource {
    suspend fun getMovies(pageNumber: Int): Result<List<RemoteMovie>>
}

interface LocalSource: DataSource {
    suspend fun addMovies(movies: List<Movie>): Result<Boolean>
    //suspend fun getMovies(pageNumber: Int): Result<List<Movie>>
    suspend fun searchForMovieByName(movieName: String): Result<List<Movie>>
    //suspend fun searchForMovieById(movieId: Int): Result<Movie>
    suspend fun deleteMovies(): Result<Boolean>

    suspend fun addGenres(genres: List<Genre>): Result<Boolean>
    suspend fun getGenreByName(genreName: String): Result<Genre>
    suspend fun deleteGenres(): Result<Boolean>

    suspend fun addMovieWithGenre(movieWithGenre: MovieGenreCrossRef): Result<Boolean>
    suspend fun getAllMovies(pageNumber: Int): Result<List<Movie>>
    suspend fun getMoviesByGenre(genreId: Int, pageNumber: Int): Result<GenreWithMovies>
    suspend fun deleteAllMoviesWithGenres(): Result<Boolean>

}

