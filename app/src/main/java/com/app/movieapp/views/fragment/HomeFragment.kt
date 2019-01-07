package com.app.movieapp.views.fragment


import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.app.movieapp.R
import com.app.movieapp.adapter.MoviePagerAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.databinding.FragmentHomeBinding
import com.app.movieapp.utility.PagerTransformer
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    private val homeVM: HomeVM by lazy {
        getActivityViewModel<HomeVM>() as HomeVM
    }
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var moviePagerAdapter: MoviePagerAdapter
    private val handler = Handler()
    private val delay: Long = 5000 //milliseconds
    private var page = 0

    private var runnable: Runnable = object : Runnable {
        override fun run() {
            if (::moviePagerAdapter.isInitialized) {
                if (moviePagerAdapter.count == page) {
                    page = 0
                } else {
                    page++
                }
                viewpager_movies.setCurrentItem(page, true)
            }
            handler.postDelayed(this, delay)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_home)
    }

    override fun initVariable() {
        mBinding = getBinding() as FragmentHomeBinding
        setViewPager()

    }

    override fun loadData() {
        homeVM.movieList.observe(this, Observer {
            mBinding.isData = true
            moviePagerAdapter = MoviePagerAdapter(childFragmentManager, it)
            viewpager_movies.adapter = moviePagerAdapter
            homeVM.setTitle(mBinding, 0)

        })

        homeVM.error.observe(this, Observer {
            mBinding.isData = true
        })
    }

    fun setViewPager() {
        viewpager_movies.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                page = position
                homeVM.setTitle(mBinding, position)
                moviePagerAdapter.notifyDataSetChanged()
            }

        })

        viewpager_movies.apply {
            clipToPadding = false
            offscreenPageLimit = 0
            setPadding(100, 0, 100, 0)
            pageMargin = 100
            setPageTransformer(false, PagerTransformer(requireContext()))
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

}
