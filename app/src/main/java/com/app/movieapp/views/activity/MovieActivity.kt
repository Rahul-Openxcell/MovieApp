package com.app.movieapp.views.activity

import android.os.Bundle
import android.view.MenuItem
import com.app.movieapp.R
import com.app.movieapp.adapter.TabAdapter
import com.app.movieapp.baseclass.BaseActivity
import com.app.movieapp.utility.KEY_KEYWORD
import com.app.movieapp.views.fragment.ComingSoonFragment
import com.app.movieapp.views.fragment.NowShowFragment
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseActivity() {

    private lateinit var keyWord: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initVariable() {
        keyWord = intent.getStringExtra(KEY_KEYWORD)
    }

    override fun loadData() {
        setTabLayout()
    }

    private fun setTabLayout() {
        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(NowShowFragment.newInstance(keyWord), getString(R.string.now_showing))
        tabAdapter.addFragment(ComingSoonFragment.newInstance(keyWord), getString(R.string.coming_soon))
        viewpager_tab.adapter = tabAdapter
        tab_movie.setupWithViewPager(viewpager_tab)
    }
}
