package com.fintold.moviesapp.dataSource.remoteSource

import com.fintold.moviesapp.BuildConfig
import com.fintold.moviesapp.dataSource.ResultOfGenresApi
import com.fintold.moviesapp.dataSource.ResultOfMoviesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val GENRES_BASE_URL = "https://api.themoviedb.org/3/genre/movie/"
const val MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val API_KEY = BuildConfig.API_KEY
const val LANGUAGE = "en-US"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val moviesRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(MOVIES_BASE_URL)
    .build()

private val genresRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GENRES_BASE_URL)
    .build()


interface MoviesApiService {
    @GET("popular?api_key=${API_KEY}&language=${LANGUAGE}")
    suspend fun getMovies(@Query("page") pageNumber: Int): ResultOfMoviesApi

    @GET("list?api_key=${API_KEY}&language=${LANGUAGE}")
    suspend fun getGenres(): ResultOfGenresApi
}

object MoviesApi {
    val moviesRetrofitService: MoviesApiService by lazy {
        moviesRetrofit.create(MoviesApiService::class.java)
    }

    val genresRetrofitService: MoviesApiService by lazy {
        genresRetrofit.create(MoviesApiService::class.java)
    }
}