package com.app.movieapp.views.fragment


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.movieapp.R
import com.app.movieapp.adapter.SearchAdapter
import com.app.movieapp.baseclass.BaseFragment
import com.app.movieapp.databinding.FragmentSearchBinding
import com.app.movieapp.db.AppDatabase
import com.app.movieapp.utility.SwipeToDeleteCallback
import com.app.movieapp.viewmodel.HomeVM
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    private val searchAdapter by lazy {
        SearchAdapter()
    }

    private val homeVM: HomeVM by lazy {
        getActivityViewModel<HomeVM>() as HomeVM
    }

    private val db by lazy {
        AppDatabase.getInstance(requireContext())
    }
    private lateinit var mBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_search)
    }

    override fun initVariable() {
        mBinding = getBinding() as FragmentSearchBinding
        setRecyclerView()
    }

    private fun setRecyclerView() {
        rv_search.adapter = searchAdapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                homeVM.deleteSearch(db, viewHolder.adapterPosition)
                searchAdapter.removeAt(viewHolder.adapterPosition)
                if (searchAdapter.searchList.isEmpty()) {
                    mBinding.isData = false
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_search)

    }

    override fun loadData() {
        homeVM.getSearchList(db)
        homeVM.searchList.observe(this, Observer {
            if (it.isNotEmpty()) {
                mBinding.isData = true
                searchAdapter.updateList(it)
            }

        })

    }
}
