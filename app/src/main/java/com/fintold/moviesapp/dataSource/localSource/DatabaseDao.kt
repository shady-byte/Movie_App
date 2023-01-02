package com.fintold.moviesapp.dataSource.localSource

import androidx.annotation.WorkerThread
import androidx.room.*
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

@Dao
interface DatabaseDao {
    //add movies to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(vararg movies: Movie): List<Long>

    //get specific movie by name
    @Query("SELECT * FROM  movies_table WHERE name LIKE '%'||:movieName||'%'")
    fun searchForMovieByName(movieName: String): List<Movie>

    //delete all movies from database
    @Query("DELETE FROM movies_table")
    fun deleteMovies()



    // add genres to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGenres(vararg genres: Genre): List<Long>

    // get genres from database
    @Query("SELECT * FROM genres_table")
    fun getGenres(): List<Genre>

    @Query("SELECT * FROM genres_table WHERE name=:genreName")
    fun getGenreByName(genreName: String): Genre

    //delete all genres from database
    @Query("DELETE FROM genres_table")
    fun deleteGenres()



    //insert each movie with its genre
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieWithGenre(movie: MovieGenreCrossRef): Long

    //get all genres with their movies
    @Transaction
    @Query("SELECT * FROM movies_table LIMIT 20 OFFSET :offset")
    fun getAllMovies(offset: Int): List<Movie>

    @Transaction
    @Query("SELECT * FROM genres_table WHERE genreId=:genreId")
    fun getMoviesByGenre(genreId: Int): GenreWithMovies

    //delete all genres with their movies from database
    @Query("DELETE FROM movie_genre_table")
    fun deleteMoviesWithGenres()



}