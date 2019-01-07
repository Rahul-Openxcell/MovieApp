package com.app.movieapp.views.fragment


import android.os.Bundle
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseFragment

class SearchFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_search)
    }

    override fun initVariable() {
    }

    override fun loadData() {
    }
}
