package com.app.movieapp.utility.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.movieapp.model.MovieListModel
import com.app.movieapp.model.MovieModel
import com.app.movieapp.model.ResponseData
import com.app.movieapp.retrofit.NetworkState
import com.app.movieapp.retrofit.RetrofitClient
import com.app.movieapp.retrofit.Status
import com.app.movieapp.utility.NETWORK_ERROR
import com.app.movieapp.utility.NOW_SHOWING
import com.app.movieapp.utility.SERVER_ERROR
import com.app.movieapp.utility.Utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.Executor

/**
 * Created by Rahul Sadhu
 */
class MovieDataSource(private var retryExecutor: Executor, val keyWord: String, var type: String) : PageKeyedDataSource<Int, MovieModel>() {
    val networkState = MutableLiveData<NetworkState>()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {
        networkState.postValue(NetworkState.LOADING)
        val call: Call<ResponseData<MovieListModel>> = RetrofitClient.getApiInterface().movieSearchList(keyWord, 1, type)
        call.enqueue(object : Callback<ResponseData<MovieListModel>> {
            override fun onResponse(call: Call<ResponseData<MovieListModel>>, response: Response<ResponseData<MovieListModel>>) {
                if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                    response.body()!!.results?.let {
                        if (type == NOW_SHOWING) {
                            callback.onResult(it.showing, null, 1)
                        } else {
                            callback.onResult(it.upcoming, null, 1)
                        }

                        networkState.postValue(NetworkState.LOADED)
                    }

                } else {
                    networkState.postValue(NetworkState(Status.FAILED, response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseData<MovieListModel>>, t: Throwable) {
                val message: String = when (t) {
                    is ConnectException -> NETWORK_ERROR
                    is UnknownHostException -> NETWORK_ERROR
                    is SocketTimeoutException -> "Please try again later…"
                    else -> SERVER_ERROR
                }
                networkState.postValue(NetworkState(Status.FAILED, message))
            }

        })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Utils.log("LoadAfter" + params.key)
        networkState.postValue(NetworkState.LOADING)
        val call: Call<ResponseData<MovieListModel>> = RetrofitClient.getApiInterface().movieSearchList(keyWord, params.key + 1, type)
        call.enqueue(object : Callback<ResponseData<MovieListModel>> {
            override fun onResponse(call: Call<ResponseData<MovieListModel>>, response: Response<ResponseData<MovieListModel>>) {
                if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                    response.body()!!.results?.let {
                        if (type == NOW_SHOWING) {
                            callback.onResult(it.showing, params.key + 1)
                        } else {
                            callback.onResult(it.upcoming, params.key + 1)
                        }

                        networkState.postValue(NetworkState.LOADED)
                    }

                } else {
                    networkState.postValue(NetworkState(Status.FAILED, response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseData<MovieListModel>>, t: Throwable) {
                val message: String = when (t) {
                    is ConnectException -> NETWORK_ERROR
                    is UnknownHostException -> NETWORK_ERROR
                    is SocketTimeoutException -> "Please try again later…"
                    else -> SERVER_ERROR
                }
                networkState.postValue(NetworkState(Status.FAILED, message))
                retry = {
                    loadAfter(params, callback)
                }
            }

        })


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Utils.log("LoadBefore " + params.key)
    }
}