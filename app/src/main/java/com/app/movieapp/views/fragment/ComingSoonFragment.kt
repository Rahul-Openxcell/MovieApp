package com.app.movieapp.views.fragment


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.movieapp.R
import com.app.movieapp.adapter.MovieAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.retrofit.NetworkState
import com.app.movieapp.utility.COMING_SHOWING
import com.app.movieapp.utility.KEY_KEYWORD
import com.app.movieapp.utility.NETWORK_ERROR
import com.app.movieapp.utility.Utils
import com.app.movieapp.viewmodel.MovieVM
import kotlinx.android.synthetic.main.fragment_coming_soon.*

class ComingSoonFragment : BaseFragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieVM: MovieVM
    private lateinit var keyWord: String

    companion object {
        fun newInstance(keyWord: String): ComingSoonFragment {
            val fragment = ComingSoonFragment()
            val args = Bundle()
            args.putString(KEY_KEYWORD, keyWord)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_coming_soon)
        keyWord = arguments?.getString(KEY_KEYWORD) ?: ""
    }

    override fun initVariable() {
        movieVM = setViewModel<MovieVM>() as MovieVM
        movieAdapter = MovieAdapter {
            movieVM.retryClick()
        }
        rv_comingShowing.adapter = movieAdapter
    }

    override fun loadData() {
        if (Utils.isNetworkAvailable(requireContext())) {
            movieVM.initPagination(keyWord, COMING_SHOWING)
        } else {
            progressbar.visibility = View.GONE
            Utils.showToast(requireContext(), NETWORK_ERROR)
        }

        movieVM.movies.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        movieVM.networkState.observe(this, Observer {
            if (it == NetworkState.LOADED) {
                progressbar.visibility = View.GONE
            }
            movieAdapter.setNetworkState(it)
        })
    }


}
