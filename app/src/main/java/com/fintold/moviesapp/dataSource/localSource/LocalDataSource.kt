package com.fintold.moviesapp.dataSource.localSource


import android.util.Log
import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef
import com.fintold.moviesapp.uI.TAG

class LocalDataSource(private val databaseDao: DatabaseDao): LocalSource {
    //MOVIES TABLE
    override suspend fun addMovies(movies: List<Movie>): Result<Boolean> {
        return try {
            val result = databaseDao.addMovies(*movies.toTypedArray())
            if(result.isNotEmpty())
                Result.Success(true)
            else
                Result.Error("Error while adding movies local source")
        } catch (ex: Exception) {
            Result.Error("Error while adding movies local source ${ex.message}")
        }
    }

    override suspend fun searchForMovieByName(movieName: String): Result<List<Movie>> {
       return try {
            val result = databaseDao.searchForMovieByName(movieName)
            if(result.isEmpty()) {
                Result.Error("Error while fetching movies by name local source")
            } else {
                Result.Success(result)
            }
        } catch (ex: Exception) {
            Result.Error("Error while fetching movies by name local source ${ex.message}")
        }
    }

    override suspend fun deleteMovies(): Result<Boolean> {
        return try {
            databaseDao.deleteMovies()
            Result.Success(true)
        } catch (ex: Exception) {
            Result.Error("Error while deleting movies local source ${ex.message}")
        }
    }


    //GENRES TABLE
    override suspend fun addGenres(genres: List<Genre>): Result<Boolean> {
        return try {
            val result = databaseDao.addGenres(*genres.toTypedArray())
            if(result.isEmpty()) {
                Result.Error("Error while adding genres local source")
            } else {
                Result.Success(true)
            }
        }catch (ex: Exception) {
            Result.Error("Error while adding genres local source ${ex.message}")
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            val result = databaseDao.getGenres()
            if(result.isEmpty()) {
               Result.Error("Error while getting genres local source")
            } else {
                Result.Success(result)
            }
        } catch (ex: Exception) {
            Result.Error("Error while getting genres local source ${ex.message}")
        }
    }

    override suspend fun getGenreByName(genreName: String): Result<Genre> {
        return try {
            val result = databaseDao.getGenreByName(genreName)
            Result.Success(result)
        } catch (ex: Exception) {
            Result.Error("Error while getting genre by name local source ${ex.message}")
        }
    }

    override suspend fun deleteGenres(): Result<Boolean> {
        return try {
            databaseDao.deleteGenres()
            Result.Success(true)
        } catch (ex: Exception) {
            Result.Error("Error while deleting genres local source ${ex.message}")
        }
    }


    //MOVIES WITH GENRES TABLE
    override suspend fun addMovieWithGenre(movieWithGenre: MovieGenreCrossRef): Result<Boolean> {
        return try {
            databaseDao.addMovieWithGenre(movieWithGenre)
            Result.Success(true)
        } catch (ex: Exception) {
            Result.Error("Error while adding movies with genres local source ${ex.message}")
        }
    }

    override suspend fun getAllMovies(pageNumber: Int): Result<List<Movie>> {
        return  try {
            val result = databaseDao.getAllMovies((pageNumber-1)*20) //(pageNumber-1)*19
            if(result.isEmpty()) {
                Result.Error("Error while getting movies local source")
            } else {
                Result.Success(result)
            }
        } catch (ex: Exception) {
            Result.Error("Error while getting movies local source ${ex.message}")
        }
    }

    override suspend fun getMoviesByGenre(genreId: Int, pageNumber: Int): Result<List<Movie>> {
        return try {
            val result = databaseDao.getMoviesByGenre(genreId)
            Result.Success(result.moviesList)

        } catch (ex: Exception) {
            Result.Error("Error while getting movies by genre local source ${ex.message}")
        }
    }

    override suspend fun deleteAllMoviesWithGenres(): Result<Boolean> {
        return try {
          databaseDao.deleteMoviesWithGenres()
          Result.Success(true)
        } catch (ex: Exception) {
            Result.Error("Error while deleting movies with genres local source ${ex.message}")
        }
    }

}