package com.app.movieapp.views.fragment


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.app.movieapp.R
import com.app.movieapp.adapter.MoviePagerAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.databinding.FragmentHomeBinding
import com.app.movieapp.utility.PagerTransformer
import com.app.movieapp.utility.Utils
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    private val homeVM: HomeVM by lazy {
        getActivityViewModel<HomeVM>() as HomeVM
    }
    private val mBinding: FragmentHomeBinding by lazy {
        getBinding() as FragmentHomeBinding
    }

    private lateinit var moviePagerAdapter: MoviePagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_home)
    }

    override fun initVariable() {
    }

    override fun loadData() {
        homeVM.movieList.observe(this, Observer {
            mBinding.isData = true
            moviePagerAdapter = MoviePagerAdapter(it)
            viewpager_movies.apply {
                adapter = moviePagerAdapter
                clipToPadding = false
                setPadding(100, 0, 100, 0)
                pageMargin = 100
                setPageTransformer(false, PagerTransformer(requireContext()))
            }
            homeVM.setTitle(mBinding, 0)

        })

        viewpager_movies.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                homeVM.setTitle(mBinding, position)
            }

        })
    }


}
