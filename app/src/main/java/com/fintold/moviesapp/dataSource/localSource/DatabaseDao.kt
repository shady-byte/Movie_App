package com.fintold.moviesapp.dataSource.localSource

import androidx.room.*
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

@Dao
interface DatabaseDao {
    //add movies to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(vararg movies: Movie)

    //get movies from database
    @Query("SELECT * FROM movies_table LIMIT 19 OFFSET (:pageNumber-1)*19")
    fun getMovies(pageNumber: Int): List<Movie>

    //get specific movie by name
    @Query("SELECT * FROM  movies_table WHERE name LIKE '%'||:movieName||'%'")
    fun searchForMovieByName(movieName: String): List<Movie>

    //get specific movie by id
    @Query("SELECT * FROM  movies_table WHERE movieId=:movieId")
    fun searchForMovieById(movieId: Int): Movie

    //delete all movies from database
    @Query("DELETE FROM movies_table")
    fun deleteMovies()



    // add genres to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGenres(vararg genres: Genre)

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
    fun addMovieWithGenres(movie: MovieGenreCrossRef)

    //get all genres with their movies
    @Transaction
    @Query("SELECT * FROM genres_table")
    fun getGenresWithMovies(): List<GenreWithMovies>

    @Transaction
    @Query("SELECT * FROM genres_table WHERE genreId=:genreId LIMIT 19 OFFSET (:pageNumber-1)*19")
    fun getOneGenreWithMovies(genreId: Int, pageNumber: Int): GenreWithMovies


    //delete all genres from database
    @Query("DELETE FROM movie_genre_table")
    fun deleteMoviesWithGenres()



}