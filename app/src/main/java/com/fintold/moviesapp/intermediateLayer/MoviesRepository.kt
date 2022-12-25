package com.fintold.moviesapp.intermediateLayer

import android.util.Log
import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import com.fintold.moviesapp.dataSource.dataClasses.GenreWithMovies
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef
import com.fintold.moviesapp.uI.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val localSource: LocalSource, private val remoteDataSource: RemoteSource): RepositoryInterface {
    override suspend fun addMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            try {
                localSource.addMovies(movies = movies)
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong while adding movies in repository ${ex.message}")
            }
        }
    }

    override suspend fun getMovies(pageNumber: Int): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = remoteDataSource.getMovies(pageNumber = pageNumber)
                if(result is Result.Success && result.data.isNotEmpty()) {
                    val movies = addMoviesGenres(result.data)
                    if(!movies.isNullOrEmpty()) {
                        addMovies(movies)
                    }
                }
                localSource.getMovies(pageNumber = pageNumber)
            }catch (ex: Exception) {
                Result.Error("Something wrong while getting movies with repository ${ex.message}")
            }
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                localSource.searchForMovieById(movieId = movieId)
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting movie by id in repository ${ex.message}")
            }
        }
    }

    override suspend fun getMoviesByName(movieName: String): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
              localSource.searchForMovieByName(movieName = movieName)
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting movies by name repository ${ex.message}")
            }
        }
    }

    override suspend fun deleteMovies() {
        withContext(Dispatchers.IO) {
            try {
                localSource.deleteMovies()
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong while removing movies in repository ${ex.message}")
            }
        }
    }




    override suspend fun addGenres(genres: List<Genre>) {
        withContext(Dispatchers.IO) {
            try {
                localSource.addGenres(genres = genres)
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong while adding genres in repository ${ex.message}")
            }
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = localSource.getGenres()
                if(result is Result.Success && result.data.isNotEmpty()) {
                    localSource.getGenres()
                } else {
                    val genres = remoteDataSource.getGenres()
                    if(genres is Result.Success && genres.data.isNotEmpty()) {
                        addGenres(genres = genres.data)
                    }
                    localSource.getGenres()
                }
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting genres")
            }
        }
    }

    override suspend fun getGenreByName(genreName: String): Result<Genre> {
        return withContext(Dispatchers.IO) {
            try {
              localSource.getGenreByName(genreName = genreName)
            } catch (ex: Exception) {
                Result.Error("Something wrong while getting genre by name repository ${ex.message}")
            }
        }
    }

    override suspend fun deleteGenres() {
        withContext(Dispatchers.IO) {
            try {
              localSource.deleteGenres()
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong while removing genres in repository ${ex.message}")
            }
        }
    }




    override suspend fun addMovieWithGenres(movieWithGenre: MovieGenreCrossRef) {
        withContext(Dispatchers.IO) {
            try {
              localSource.addMovieWithGenres(movieWithGenre)
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong wadding movies with genres in repository ${ex.message}")
            }
        }
    }

    override suspend fun getGenresWithMovies(): Result<List<GenreWithMovies>> {
        return withContext(Dispatchers.IO) {
            try {
                localSource.getGenresWithMovies()
            } catch (ex: Exception) {
                Result.Error("Something wrong with getting genres with movies repository ${ex.message}")
            }
        }
    }

    override suspend fun getOneGenreWithMovies(genreId: Int,pageNumber: Int): Result<GenreWithMovies> {
        return withContext(Dispatchers.IO) {
            try {
                localSource.getOneGenreWithMovies(genreId = genreId,pageNumber = pageNumber)
            } catch (ex: Exception) {
                Result.Error("Something wrong with getting one genre with movies repository ${ex.message}")
            }
        }
    }

    override suspend fun deleteMoviesWithGenres() {
        withContext(Dispatchers.IO) {
            try {
                localSource.deleteMoviesWithGenres()
            } catch (ex: Exception) {
                Log.d(TAG,"Something wrong while removing movies with genres in repository ${ex.message}")
            }
        }
    }



    private suspend fun addMoviesGenres(movies: List<RemoteMovie>): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val moviesList = mutableListOf<Movie>()
            try {
                for(movie in movies) {
                    val localMovie = Movie(imageUrl = movie.imageUrl, name = movie.name,
                        productionDate = movie.productionDate, rating = movie.rating,
                        description = movie.description, movieId =movie.movieId)
                    moviesList.add(localMovie)
                    for(genre in movie.genre) {
                        val movieWithGenre = MovieGenreCrossRef(movieId = movie.movieId, genreId = genre)
                        addMovieWithGenres(movieWithGenre)
                    }
                }
                moviesList
            } catch (ex: Exception) {
                null
            }
        }
    }

}

