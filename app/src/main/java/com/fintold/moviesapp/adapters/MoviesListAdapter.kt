package com.fintold.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.databinding.MovieItemLayoutBinding
import com.fintold.moviesapp.uI.MoviesViewModel

class MoviesListAdapter(private val clickListener: OnClickListener, private val viewModel: MoviesViewModel):
    ListAdapter<Movie,MoviesListAdapter.ViewHolder>(MovieCallBack){

    companion object MovieCallBack: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }


    inner class ViewHolder(private val binding: MovieItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieDisplayed = movie
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            clickListener.clickListener(movie)
        }
    }

}

class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}