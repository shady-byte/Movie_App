package com.fintold.moviesapp.adapters

import android.net.Uri
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fintold.moviesapp.R
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.dataSource.remoteSource.MOVIE_IMAGE_BASE_URL
import com.fintold.moviesapp.uI.MoviesViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


@BindingAdapter("MovieListAdapter")
fun RecyclerView.bindAdapter(moviesList: List<Movie>?) {
    if(moviesList.isNullOrEmpty() || moviesList.isEmpty())
        visibility = View.GONE
    moviesList?.let {
        visibility = View.VISIBLE
        val adapter = this.adapter as MoviesListAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("MovieImageAdapter")
fun ImageView.bindImage(imgUrl: String?) {
    val fullImgUrl = MOVIE_IMAGE_BASE_URL + imgUrl
    val imgUri = Uri.parse(fullImgUrl)
    imgUrl?.let {
        Picasso.get()
            .load(imgUri)
            .placeholder(R.drawable.ic_loading_image_icon)
            .into(this)
    }
}

@BindingAdapter("MovieProductionDateAdapter")
fun TextView.bindText(date: String?) {
    date?.let{
        val split = date.split("-")
        this.text = context.getString(R.string.production_date_format,split[0]) //R.string.production_date_format
        //Log.d(TAG,split[0])
    }
}

@BindingAdapter("LoadingMoviesAdapter")
fun ProgressBar.bindVisibility(moviesList: List<Movie>?) {
    visibility = if(moviesList.isNullOrEmpty())
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("TextVisibilityAdapter")
fun TextView.bindVisibility(moviesList: List<Movie>?) {
    visibility = if(moviesList.isNullOrEmpty())
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("MovieProductionDateAndRatingAdapter")
fun TextView.bindMovieData(movie: Movie?) {
    movie?.let{
        val split = movie.productionDate.split("-")
        this.text = context.getString(R.string.movie_date_rating_format,split[0],movie.rating) //R.string.production_date_format
        //Log.d(TAG,split[0])
    }
}

