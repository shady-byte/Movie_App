package com.fintold.moviesapp.uI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.intermediateLayer.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "MainTest"
class MoviesViewModel(private val repository: RepositoryInterface): ViewModel() {
    private val _moviesList = MutableLiveData<List<Movie>?>()
    val moviesList: LiveData<List<Movie>?>
        get() = _moviesList

    private val _movieGenres = MutableLiveData<List<Genre>>()
    val movieGenres: LiveData<List<Genre>>
        get() = _movieGenres

    private val _searchMovieList = MutableLiveData<List<Movie>?>()
    val searchMovieList: LiveData<List<Movie>?>
        get() = _searchMovieList

    val searchKeyWord = MutableLiveData<String>()
    val genreDisplayed = MutableLiveData<String>("All")
    var pageNumber = 1


    fun getAllMovies() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getMoviesFromRemoteSource(pageNumber)
            }
            if(result is Result.Success<List<Movie>>) {
                addMoviesToBeDisplayed(result.data)
                pageNumber++
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getGenres()
            }
            if(result is Result.Success) {
                _movieGenres.value =result.data
            }
        }
    }

    fun searchForMovieByName() {
        viewModelScope.launch {
            if(!searchKeyWord.value.isNullOrEmpty()) {
                val result = withContext(Dispatchers.IO){
                    repository.getMoviesByName(searchKeyWord.value!!)
                }
                if(result is Result.Success<List<Movie>> && result.data.isNotEmpty()) {
                    _searchMovieList.value = result.data
                } else {
                    _searchMovieList.value = null
                }
            }
        }
    }

    fun getMoviesByGenre() {
        viewModelScope.launch {
            if(genreDisplayed.value == "All") {
                getAllMovies()
            } else {
                val result = withContext(Dispatchers.IO) {
                    repository.getGenreByName(genreDisplayed.value!!)
                }
                if(result is Result.Success) {
                    val moviesResult = withContext(Dispatchers.IO) {
                        repository.getMoviesByGenre(genreId = result.data.genreId,pageNumber)
                    }
                    if(moviesResult is Result.Success) {
                        addMoviesToBeDisplayed(moviesResult.data)
                        pageNumber++
                    }
                }
            }
        }
    }

    fun clearMoviesList() {
        _moviesList.value = null
        pageNumber = 1
    }

    private fun addMoviesToBeDisplayed(movies: List<Movie>) {
        viewModelScope.launch {
            if(moviesList.value == null) {
                _moviesList.value = movies
            } else {
                val oldList = moviesList.value
                _moviesList.value = oldList?.plus(movies)
            }
        }
    }

    fun deleteAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovies()
            repository.deleteMoviesWithGenres()
        }
    }

}