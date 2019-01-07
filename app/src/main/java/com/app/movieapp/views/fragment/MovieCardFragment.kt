package com.app.movieapp.views.fragment


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController

import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.databinding.FragmentMovieCardBinding
import com.app.movieapp.model.MovieModel
import com.app.movieapp.utility.KEY_MOVIE_MODEL
import com.app.movieapp.utility.Utils
import com.app.movieapp.views.activity.MovieActivity

class MovieCardFragment : BaseFragment() {

    private lateinit var movieModel: MovieModel
    private lateinit var mBinding: FragmentMovieCardBinding

    companion object {
        fun newInstance(movieModel: MovieModel): MovieCardFragment {
            val fragment = MovieCardFragment()
            val args = Bundle()
            args.putParcelable(KEY_MOVIE_MODEL, movieModel)
            fragment.arguments = args
            return fragment
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_movie_card)
        movieModel = arguments?.getParcelable(KEY_MOVIE_MODEL) ?: MovieModel()
    }

    override fun initVariable() {
        mBinding = getBinding() as FragmentMovieCardBinding
        mBinding.movieModel = movieModel
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (::mBinding.isInitialized) {
            mBinding.btnBuyTicket.visibility = if (isVisibleToUser) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun loadData() {
        mBinding.root.setOnClickListener {
            Utils.startNewActivity(requireContext(), Intent(context, MovieActivity::class.java))
        }
    }


}
