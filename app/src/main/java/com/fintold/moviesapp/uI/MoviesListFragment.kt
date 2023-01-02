package com.fintold.moviesapp.uI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fintold.moviesapp.R
import com.fintold.moviesapp.adapters.MoviesListAdapter
import com.fintold.moviesapp.adapters.OnClickListener
import com.fintold.moviesapp.dataSource.Movie
import com.fintold.moviesapp.databinding.ChipItemLayoutBinding
import com.fintold.moviesapp.databinding.FragmentMoviesListBinding
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesListFragment : Fragment() {
    private var binding: FragmentMoviesListBinding ?=null
    private val viewModel by sharedViewModel<MoviesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)

        addGenres(container)

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        binding?.moviesListRecyclerView?.adapter = MoviesListAdapter(OnClickListener {
            navigateToMovieDetails(it)
        })


        binding?.moviesListRecyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && viewModel.genreDisplayed.value == "All") {
                    viewModel.getAllMovies()
                }
            }
        })

        //binding?.moviesListRecyclerView?.itemAnimator = null


        binding?.homeSearchView?.searchView?.setEndIconOnClickListener {
            viewModel.searchForMovieByName()
            navigateToSearchFragment()
        }
        handleChipsSelection()
        searchViewListener()

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).supportActionBar?.hide()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateToMovieDetails(movie: Movie) {
        findNavController().navigate(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movie))
    }

    private fun navigateToSearchFragment() {
        findNavController().navigate(MoviesListFragmentDirections.actionMoviesListFragmentToSearchFragment())
    }

    private fun addGenres(container: ViewGroup?) {
        viewModel.movieGenres.observe(viewLifecycleOwner) {
            var num = 1
            it?.let {
                for(genre in it) {
                    val chipView: ChipItemLayoutBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parentFragment?.context),R.layout.chip_item_layout,container,false)
                    chipView.chipName = genre.name
                    chipView.root.id = View.generateViewId()
                    binding?.genresChipGroup?.addView(chipView.root)
                    num++
                }
            }
        }
    }

    private fun searchViewListener() {
        binding?.homeSearchView?.searchView?.editText?.setOnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchForMovieByName()
                navigateToSearchFragment()
            }
            return@setOnEditorActionListener true
        }
    }

    private fun handleChipsSelection() {
        binding?.genresChipGroup?.setOnCheckedStateChangeListener { group, _ ->
            var i = 0
            while (i < group.childCount) {
                val chip = group.getChildAt(i) as Chip
                if (chip.isChecked) {
                    viewModel.genreDisplayed.postValue(chip.text.toString())
                }
                i++
            }
            viewModel.clearMoviesList()
        }

        viewModel.genreDisplayed.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.getMoviesByGenre()
            }
        }
    }

}

