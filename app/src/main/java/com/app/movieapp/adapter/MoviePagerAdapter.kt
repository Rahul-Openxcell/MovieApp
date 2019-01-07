package com.app.movieapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.movieapp.model.MovieModel
import com.app.movieapp.views.fragment.MovieCardFragment

class MoviePagerAdapter(fragmentManager: FragmentManager, private val movieList: ArrayList<MovieModel>) : FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return MovieCardFragment.newInstance(movieList[position])
    }

    override fun getCount(): Int {
        return movieList.size
    }
}