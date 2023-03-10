package com.fintold.moviesapp.intermediateLayer

import  com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import com.fintold.moviesapp.dataSource.dataClasses.MovieGenreCrossRef

class MoviesRepository(private val localSource: LocalSource, private val remoteDataSource: RemoteSource): RepositoryInterface {
    //MOVIES TABLE
    override suspend fun addMovies(movies: List<Movie>): Result<Boolean> {
        return try {
            localSource.addMovies(movies)
        } catch (ex: Exception) {
            Result.Error("Error while adding movies in repository ${ex.message}")
        }
    }

    override suspend fun getMoviesByName(movieName: String): Result<List<Movie>> {
        return try {
          localSource.searchForMovieByName(movieName = movieName)
        } catch (ex: Exception) {
            Result.Error("Error while getting movies by name in repository ${ex.message}")
        }

    }

    override suspend fun deleteMovies(): Result<Boolean> {
        return try {
            localSource.deleteMovies()
        } catch (ex: Exception) {
           Result.Error("Error while deleting movies in repository ${ex.message}")
        }
    }


    //GENRES TABLE
    override suspend fun addGenres(genres: List<Genre>): Result<Boolean> {
        return try {
            localSource.addGenres(genres = genres)
        } catch (ex: Exception) {
            Result.Error("Error while adding genres in repository ${ex.message}")
        }

    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            val result = localSource.getGenres()
            if(result is Result.Success && result.data.isNotEmpty()) {
                result
            } else {
                val genres = remoteDataSource.getGenres()
                if(genres is Result.Success && genres.data.isNotEmpty()) {
                    addGenres(genres = genres.data)
                }
                localSource.getGenres()
            }
        } catch (ex: Exception) {
            Result.Error("Error while getting genres in repository ${ex.message}")
        }
    }

    override suspend fun getGenreByName(genreName: String): Result<Genre> {
        return try {
          localSource.getGenreByName(genreName = genreName)
        } catch (ex: Exception) {
            Result.Error("Error while getting genre by name in repository ${ex.message}")
        }
    }

    override suspend fun deleteGenres(): Result<Boolean> {
        return try {
          localSource.deleteGenres()
        } catch (ex: Exception) {
            Result.Error("Error while removing genres in repository ${ex.message}")
        }
    }


    //MOVIES WITH GENRES TABLE
    override suspend fun addMovieWithGenre(movieWithGenre: MovieGenreCrossRef): Result<Boolean> {
        return try {
            localSource.addMovieWithGenre(movieWithGenre)
        } catch (ex: Exception) {
            Result.Error("Error while adding movie with genre in repository ${ex.message}")
        }
    }

    override suspend fun getAllMovies(pageNumber: Int): Result<List<Movie>> {
        return try {
            localSource.getAllMovies(pageNumber)
        } catch (ex: Exception) {
            Result.Error("Error while getting all movies in repository ${ex.message}")
        }
    }

    override suspend fun getMoviesByGenre(genreId: Int,pageNumber: Int): Result<List<Movie>> {
        return try {
            localSource.getMoviesByGenre(genreId = genreId,pageNumber = pageNumber)
        } catch (ex: Exception) {
            Result.Error("Error while getting movies by genre in repository ${ex.message}")
        }
    }

    override suspend fun deleteMoviesWithGenres(): Result<Boolean> {
        return try {
            localSource.deleteAllMoviesWithGenres()
        } catch (ex: Exception) {
            Result.Error("Error while deleting movies with genres in repository ${ex.message}")
        }
    }

    override suspend fun getMoviesFromRemoteSource(pageNumber: Int): Result<List<Movie>> {
        return try {
            val result = remoteDataSource.getMovies(pageNumber)
            if(result is Result.Success<List<RemoteMovie>>) {
                val movies = addMoviesWithGenres(result.data)
                if(movies!=null && movies.isNotEmpty()) {
                    localSource.addMovies(movies)
                    Result.Success(movies)
                } else {
                    Result.Error("Error while getting data from Api")
                }
            } else {
                localSource.getAllMovies(pageNumber)
            }
        }catch (ex: Exception) {
            Result.Error("Error while getting data from Api ${ex.message}")
        }
    }

    private suspend fun addMoviesWithGenres(movies: List<RemoteMovie>): List<Movie>? {
        val movieList = mutableListOf<Movie>()
        return try {
            for(movie in movies) {
                val localMovie = Movie(imageUrl = movie.imageUrl, name = movie.name,
                    productionDate = movie.productionDate, rating = movie.rating,
                    description = movie.description, movieId =movie.movieId)
                movieList.add(localMovie)
                for (genre in movie.genre) {
                    val movieWithGenre = MovieGenreCrossRef(movieId = movie.movieId, genreId = genre)
                    addMovieWithGenre(movieWithGenre)
                }
            }
            movieList
        }catch (ex: Exception) {
            null
        }

    }

}

