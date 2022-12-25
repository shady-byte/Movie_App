package com.fintold.moviesapp.uI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.fintold.moviesapp.BuildConfig
import com.fintold.moviesapp.R
import com.fintold.moviesapp.databinding.FragmentMovieDetailsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment : Fragment() {
    private var binding: FragmentMovieDetailsBinding ?=null
    private val viewModel by sharedViewModel<MoviesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        //binding?.lifecycleOwner = viewLifecycleOwner
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            it?.let {
                binding?.movieDisplayed = it
                (requireActivity() as MainActivity).setActionBarTitle(it.name)
            }
        }

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}