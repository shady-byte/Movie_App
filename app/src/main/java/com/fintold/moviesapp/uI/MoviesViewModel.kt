package com.fintold.moviesapp.uI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintold.moviesapp.adapters.Result
import com.fintold.moviesapp.dataSource.Genre
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.intermediateLayer.RepositoryInterface
import kotlinx.coroutines.launch

const val TAG = "MainTest"
class MoviesViewModel(private val repository: RepositoryInterface): ViewModel() {
    private val _moviesList = MutableLiveData<List<Movie>?>()
    val moviesList: LiveData<List<Movie>?>
        get() = _moviesList

    private val _movieGenres = MutableLiveData<List<Genre>>()
    val movieGenres: LiveData<List<Genre>>
        get() = _movieGenres

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _searchMovieList = MutableLiveData<List<Movie>>()
    val searchMovieList: LiveData<List<Movie>>
        get() = _searchMovieList

    val searchKeyWord = MutableLiveData<String>()
    val genreDisplayed = MutableLiveData<String>("All")
    var pageNumber = 1

    fun getMoreMovies() {
        viewModelScope.launch {
            repository.getMovies(pageNumber)
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            val result = repository.getGenres()
            if(result is Result.Success) {
                _movieGenres.value =result.data
            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }


    fun searchForMovieByName() {
        viewModelScope.launch {
            if(!searchKeyWord.value.isNullOrEmpty()) {
                val result = repository.getMoviesByName(searchKeyWord.value!!)
                if(result is Result.Success<List<Movie>>) {
                    _searchMovieList.value = result.data
                }
            }
        }
    }

    fun getMoviesByGenre() {
        viewModelScope.launch {
            if(genreDisplayed.value == "All") {
                val result = repository.getMovies(pageNumber)
                if(result is Result.Success) {
                    addMoviesToBeDisplayed(result.data)
                }
            } else {
                val result = repository.getGenreByName(genreDisplayed.value!!)
                if(result is Result.Success) {
                    val moviesResult = repository.getOneGenreWithMovies(genreId = result.data.genreId,pageNumber)
                    if(moviesResult is Result.Success) {
                        val movies = moviesResult.data.moviesList
                        addMoviesToBeDisplayed(movies)
                    }
                }
            }
        }
    }

    fun clearMovies() {
        _moviesList.value = null
        pageNumber = 1
    }

    private fun addMoviesToBeDisplayed(movies: List<Movie>) {
        viewModelScope.launch {
            if(_moviesList.value.isNullOrEmpty()) {
                _moviesList.value = movies
            } else {
                val oldList = _moviesList.value
                _moviesList.value = oldList?.plus(movies)
            }
        }
    }


}