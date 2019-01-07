package com.app.movieapp.views.fragment


import android.os.Bundle
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.databinding.FragmentMovieCardBinding
import com.app.movieapp.model.MovieModel
import com.app.movieapp.utility.KEY_MOVIE_MODEL

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

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (::mBinding.isInitialized) {
            if (isVisibleToUser) {
                mBinding.btnBuyTicket.animate().alpha(1.0f).duration = 500
            } else {
                mBinding.btnBuyTicket.animate().alpha(0.0f).duration = 500
            }
        }


    }

    override fun loadData() {
        mBinding.movieModel = movieModel
    }


}
