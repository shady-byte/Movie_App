package com.fintold.moviesapp.dataSource.remoteSource

import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RemoteDataSource: RemoteSource {
    override suspend fun getMovies(pageNumber: Int): Result<List<RemoteMovie>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = MoviesApi.moviesRetrofitService.getMovies(pageNumber = pageNumber)
                val movies = result.results
                //val localMovies = convertRemoteMovieToMovie(movies)
                if(movies.isNotEmpty()) {
                    Result.Success(movies)
                } else {
                    Result.Error("Something went wrong from remote data source")
                }
            } catch (ex: Exception) {
                Result.Error("Something went wrong from remote data source ${ex.message}")
            }
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = MoviesApi.genresRetrofitService.getGenres()
                val genres = result.genres
                Result.Success(genres)
            } catch (ex: Exception) {
                Result.Error("Something went wrong with genres ${ex.message}")
            }
        }
    }
}

private fun getFullImageUrlForMovies(movies: List<Movie>): List<Movie>? {
    return try {
        movies.map {
            it.imageUrl = MOVIE_IMAGE_BASE_URL + it.imageUrl
        }
        movies
    } catch(ex: Exception) {
        null
    }
}

private fun convertRemoteMovieToMovie(movies: List<RemoteMovie>): List<Movie>? {
    return try {
        val localMovies = mutableListOf<Movie>()
        movies.forEach {
            val movie = Movie(imageUrl = it.imageUrl, name = it.name, productionDate = it.productionDate,
               rating = it.rating, description = it.description, movieId = it.movieId)
            localMovies.add(movie)
        }
        localMovies
    } catch (ex: Exception) {
        null
    }
}

private fun addMoviesWithGenresToTable() {}
