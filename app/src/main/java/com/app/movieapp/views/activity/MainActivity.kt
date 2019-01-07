package com.app.movieapp.views.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseActivity
import com.app.movieapp.db.AppDatabase
import com.app.movieapp.db.SearchEntity
import com.app.movieapp.utility.KEY_KEYWORD
import com.app.movieapp.utility.Utils
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {

    private val homeVM by lazy {
        getViewModel<HomeVM>() as HomeVM
    }

    private val db by lazy {
        AppDatabase.getInstance(this)
    }
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initVariable() {
        navController = findNavController(R.id.fragment)
    }

    override fun loadData() {
        homeVM.callMovieList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu?.findItem(R.id.searchFragment)
        val searchView = searchMenuItem?.actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search_hint)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(this@MainActivity)
        }

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // searchView expanded
            } else {
                toolbar.collapseActionView()
                navController.popBackStack()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        Utils.log("query : ${query.trim()}")
        db.searchDao().insert(SearchEntity(name = query.trim()))
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(KEY_KEYWORD, query.trim())
        Utils.startNewActivity(this, intent)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Utils.log("newText : $newText")
        return true
    }

    override fun apiError(error: String) {
        super.apiError(error)
        homeVM.error.value = error

    }

}
