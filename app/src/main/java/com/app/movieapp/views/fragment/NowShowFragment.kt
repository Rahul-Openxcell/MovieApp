package com.app.movieapp.views.fragment


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.app.movieapp.R
import com.app.movieapp.adapter.MovieAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.retrofit.NetworkState
import com.app.movieapp.utility.NOW_SHOWING
import com.app.movieapp.viewmodel.MovieVM
import kotlinx.android.synthetic.main.fragment_now_show.*


class NowShowFragment : BaseFragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieVM: MovieVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_now_show)
    }


    override fun initVariable() {
        movieVM = setViewModel<MovieVM>() as MovieVM
        movieAdapter = MovieAdapter {
            movieVM.retryClick()
        }
        rv_nowShowing.adapter = movieAdapter

    }

    override fun loadData() {
        movieVM.initPagination("", NOW_SHOWING)
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