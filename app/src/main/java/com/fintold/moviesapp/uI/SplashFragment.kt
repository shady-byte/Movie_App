package com.fintold.moviesapp.uI

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.fintold.moviesapp.R
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding? = null
    private val viewModel by sharedViewModel<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        (requireActivity() as MainActivity).supportActionBar?.hide()


        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMoviesListFragment())
            }
        }.start()
        //viewModel.deleteAllMovies()
        viewModel.getGenres()
        viewModel.getAllMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
