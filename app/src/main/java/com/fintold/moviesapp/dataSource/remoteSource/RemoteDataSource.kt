package com.fintold.moviesapp.dataSource.remoteSource

import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

class RemoteDataSource: RemoteSource {
    override suspend fun getMovies(pageNumber: Int): Result<List<RemoteMovie>> {
        return try {
            val result = MoviesApi.moviesRetrofitService.getMovies(pageNumber)
            val movies = result.results
            if(movies.isEmpty()) {
                Result.Error("Error while getting movies remote source")
            } else {
                Result.Success(movies)
            }
        }catch (ex: Exception) {
            Result.Error("Error while getting movies remote source ${ex.message}")
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            val result = MoviesApi.genresRetrofitService.getGenres()
            val genres = result.genres
            if(genres.isEmpty()) {
                Result.Error("Error while getting genres remote source")
            } else {
                Result.Success(genres)
            }
        } catch (ex: Exception) {
            Result.Error("Error while getting genres remote source ${ex.message}")
        }
    }
}
