package com.app.movieapp.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.app.movieapp.R
import com.app.movieapp.adapter.TabAdapter
import com.app.movieapp.baseclass.BaseActivity
import com.app.movieapp.views.fragment.ComingSoonFragment
import com.app.movieapp.views.fragment.NowShowFragment
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseActivity() {


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
        setTabLayout()
    }

    override fun loadData() {

    }

    private fun setTabLayout() {
        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(NowShowFragment(), getString(R.string.now_showing))
        tabAdapter.addFragment(ComingSoonFragment(), getString(R.string.coming_soon))
        viewpager_tab.adapter = tabAdapter
        tab_movie.setupWithViewPager(viewpager_tab)
    }
}
