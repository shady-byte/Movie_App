package com.fintold.moviesapp.uI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.fintold.moviesapp.R
import com.fintold.moviesapp.adapters.MoviesListAdapter
import com.fintold.moviesapp.adapters.OnClickListener
import com.fintold.moviesapp.databinding.FragmentSearchBinding
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding ?=null
    private val viewModel by sharedViewModel<MoviesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.searchScreenSearchView?.searchView?.setEndIconOnClickListener {
            viewModel.searchForMovieByName()
            UIUtil.hideKeyboard(requireActivity())
        }
        searchViewListener()

        binding?.moviesSearchResultRecyclerView?.adapter = MoviesListAdapter(OnClickListener {
            viewModel.setSelectedMovie(it)
            navigateToMovieDetails()
        },viewModel)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //(requireActivity() as MainActivity).supportActionBar?.hide()
        viewModel.searchKeyWord.postValue("")
        binding = null
    }

    private fun searchViewListener() {
        binding?.searchScreenSearchView?.searchView?.editText?.setOnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchForMovieByName()
                UIUtil.hideKeyboard(requireActivity())
            }
            return@setOnEditorActionListener true
        }
    }

    private fun navigateToMovieDetails() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment())
    }
}