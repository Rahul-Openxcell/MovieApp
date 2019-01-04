package com.app.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.app.movieapp.R
import com.app.movieapp.databinding.ItemMovieBinding
import com.app.movieapp.model.MovieModel

class MoviePagerAdapter(private val movieList: ArrayList<MovieModel>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemMovieBinding: ItemMovieBinding =
            DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.item_movie, container, false)
        itemMovieBinding.movieModel = movieList[position]
        itemMovieBinding.executePendingBindings()
        container.addView(itemMovieBinding.root)
        return itemMovieBinding.root
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun getCount(): Int {
        return movieList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}