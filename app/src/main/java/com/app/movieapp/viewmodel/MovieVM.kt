package com.app.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.movieapp.baseclass.BaseViewModel
import com.app.movieapp.model.MovieModel
import com.app.movieapp.retrofit.NetworkState
import com.app.movieapp.utility.datasource.MovieDataSource
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MovieVM : BaseViewModel() {
    var networkState: LiveData<NetworkState> = MutableLiveData<NetworkState>()
    lateinit var movies: LiveData<PagedList<MovieModel>>
    private var dataSourceLiveData = MutableLiveData<MovieDataSource>()
    private lateinit var executor: Executor
    private lateinit var movieDataSource: MovieDataSource

    fun initPagination(keyWord: String, type: String) {

        executor = Executors.newFixedThreadPool(5)

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .build()

        val dataFactory = object : DataSource.Factory<Int, MovieModel>() {
            override fun create(): DataSource<Int, MovieModel> {
                movieDataSource = MovieDataSource(executor, keyWord, type)
                dataSourceLiveData.postValue(movieDataSource)
                return movieDataSource
            }
        }

        movies = LivePagedListBuilder(dataFactory, config).build()
        networkState = Transformations.switchMap(dataSourceLiveData, MovieDataSource::networkState)

    }

    fun retryClick() {
        movieDataSource.retryAllFailed()

    }
}