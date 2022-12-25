package com.fintold.moviesapp.dataSource.localSource

import android.util.Log
import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef
import com.fintold.moviesapp.uI.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(private val databaseDao: DatabaseDao): LocalSource {
    override suspend fun addMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            try {
                databaseDao.addMovies(*movies.toTypedArray())
            } catch (ex: Exception) {
            }
        }
    }

    override suspend fun getMovies(pageNumber: Int): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                val movies = databaseDao.getMovies(pageNumber)
                Result.Success(movies)
            } catch (ex:Exception) {
                Result.Error("Something went wrong with fetching movies ${ex.message}")
            }
        }
    }

    override suspend fun searchForMovieByName(movieName: String): Result<List<Movie>> {
        return  withContext(Dispatchers.IO) {
            try {
                val movies = databaseDao.searchForMovieByName(movieName)
                Result.Success(movies)
            } catch (ex: java.lang.Exception) {
                Result.Error("Something went wrong with searching for movie ${ex.message}")
            }
        }
    }

    override suspend fun searchForMovieById(movieId: Int): Result<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                val result = databaseDao.searchForMovieById(movieId = movieId)
                Result.Success(result)
            } catch (ex: Exception) {
                Result.Error("movie id is not found")
            }
        }
    }

    override suspend fun deleteMovies() {
        withContext(Dispatchers.IO) {
            try {
                databaseDao.deleteMovies()
            } catch (ex: Exception) {
            }
        }
    }




    override suspend fun addGenres(genres: List<Genre>) {
        withContext(Dispatchers.IO) {
            try {
                databaseDao.addGenres(*genres.toTypedArray())
            } catch (ex: Exception) {
            }
        }
    }

    override suspend fun getGenreByName(genreName: String): Result<Genre> {
        return withContext(Dispatchers.IO) {
            try {
                val result = databaseDao.getGenreByName(genreName = genreName)
                Result.Success(result)
            } catch (ex:Exception) {
                Result.Error("Something wrong while getting genre by name local ${ex.message}")
            }

        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return withContext(Dispatchers.IO) {
            try {
                val genres = databaseDao.getGenres()
                Result.Success(genres)
            } catch (ex:Exception) {
                Result.Error("Something went wrong with fetching genres ${ex.message}")
            }
        }
    }

    override suspend fun deleteGenres() {
        withContext(Dispatchers.IO) {
            try {
                databaseDao.deleteGenres()
            } catch (es: Exception) {
            }
        }
    }




    override suspend fun addMovieWithGenres(movieWithGenre: MovieGenreCrossRef) {
        withContext(Dispatchers.IO) {
            try {
              databaseDao.addMovieWithGenres(movie = movieWithGenre)
            } catch (ex: Exception) {
            }
        }
    }

    override suspend fun getGenresWithMovies(): Result<List<GenreWithMovies>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = databaseDao.getGenresWithMovies()
                Result.Success(result)
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting movies with genres local ${ex.message}")
            }
        }
    }

    override suspend fun getOneGenreWithMovies(genreId: Int, pageNumber: Int): Result<GenreWithMovies> {
        return withContext(Dispatchers.IO) {
            try {
                val result = databaseDao.getOneGenreWithMovies(genreId = genreId, pageNumber = pageNumber)
                Result.Success(result)
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting one genre with movies local ${ex.message}")
            }
        }
    }

    override suspend fun deleteMoviesWithGenres() {
        withContext(Dispatchers.IO) {
            try {
                databaseDao.deleteMoviesWithGenres()
            } catch (ex: Exception) {
            }
        }
    }

}