package com.app.movieapp.views.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseActivity
import com.app.movieapp.utility.Utils
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {

    private val homeVM by lazy {
        getViewModel<HomeVM>() as HomeVM
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
        /*  val appBarConfiguration = AppBarConfiguration(navController.graph)
          toolbar.setupWithNavController(navController, appBarConfiguration)*/
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


    override fun onQueryTextSubmit(query: String?): Boolean {
        Utils.log("query : $query")
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Utils.log("newText : $newText")
        return true
    }
}
