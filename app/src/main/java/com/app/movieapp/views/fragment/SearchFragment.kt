package com.app.movieapp.views.fragment


import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.movieapp.R
import com.app.movieapp.adapter.SearchAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.db.AppDatabase
import com.app.movieapp.utility.SwipeToDeleteCallback
import com.app.movieapp.utility.Utils
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    private val searchAdapter by lazy {
        SearchAdapter()
    }

    private val homeVM: HomeVM by lazy {
        getActivityViewModel<HomeVM>() as HomeVM
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_search)
    }

    override fun initVariable() {
        setRecyclerView()
    }

    private fun setRecyclerView() {
        rv_search.adapter = searchAdapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_search.adapter as SearchAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_search)

    }

    override fun loadData() {
        homeVM.getSearchList(AppDatabase.getInstance(requireContext()))
        homeVM.searchList.observe(this, Observer {
            Utils.log("search List : ${it}")
            searchAdapter.updateList(it)
        })

    }
}
