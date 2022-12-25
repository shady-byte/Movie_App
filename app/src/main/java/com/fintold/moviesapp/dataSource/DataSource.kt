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
    suspend fun addMovies(movies: List<Movie>)
    suspend fun getMovies(pageNumber: Int): Result<List<Movie>>
    suspend fun searchForMovieByName(movieName: String): Result<List<Movie>>
    suspend fun searchForMovieById(movieId: Int): Result<Movie>
    suspend fun deleteMovies()

    suspend fun addGenres(genres: List<Genre>)
    suspend fun getGenreByName(genreName: String): Result<Genre>
    suspend fun deleteGenres()

    suspend fun addMovieWithGenres(movieWithGenre: MovieGenreCrossRef)
    suspend fun getGenresWithMovies(): Result<List<GenreWithMovies>>
    suspend fun getOneGenreWithMovies(genreId: Int, pageNumber: Int): Result<GenreWithMovies>
    suspend fun deleteMoviesWithGenres()

}

