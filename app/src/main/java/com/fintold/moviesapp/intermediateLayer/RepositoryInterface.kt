package com.fintold.moviesapp.intermediateLayer

import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

interface RepositoryInterface {
    //MOVIES TABLE
    suspend fun addMovies(movies: List<Movie>): Result<Boolean>
    suspend fun updateMovies(pageNumber: Int): Result<Boolean>
    //suspend fun getMovies(pageNumber: Int): Result<List<Movie>>
    suspend fun getMoviesByName(movieName: String): Result<List<Movie>>
    //suspend fun getMovieById(movieId: Int): Result<Movie>
    suspend fun deleteMovies(): Result<Boolean>

    //GENRES TABLE
    suspend fun addGenres(genres: List<Genre>): Result<Boolean>
    suspend fun getGenres(): Result<List<Genre>>
    suspend fun getGenreByName(genreName: String): Result<Genre>
    suspend fun deleteGenres(): Result<Boolean>

    //MOVIES WITH GENRES TABLE
    suspend fun addMovieWithGenre(movieWithGenre: MovieGenreCrossRef): Result<Boolean>
    suspend fun getAllMovies(pageNumber: Int): Result<List<Movie>>
    suspend fun getMoviesByGenre(genreId: Int,pageNumber: Int): Result<GenreWithMovies>
    suspend fun deleteMoviesWithGenres(): Result<Boolean>

}