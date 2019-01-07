package com.app.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.movieapp.baseclass.BaseViewModel
import com.app.movieapp.baseclass.SingleLiveData
import com.app.movieapp.databinding.FragmentHomeBinding
import com.app.movieapp.db.AppDatabase
import com.app.movieapp.db.SearchEntity
import com.app.movieapp.model.GenreModel
import com.app.movieapp.model.MovieModel
import com.app.movieapp.model.ResponseData
import com.app.movieapp.retrofit.MOVIE_LIST_URL
import com.app.movieapp.retrofit.RetrofitClient

class HomeVM : BaseViewModel() {

    var movieList = MutableLiveData<ArrayList<MovieModel>>()
    lateinit var searchList: LiveData<List<SearchEntity>>
    var error = SingleLiveData<String>()


    fun callMovieList() {
        val call = RetrofitClient.getApiInterface().movieList()
        getNetworkManager().requestData(call, MOVIE_LIST_URL)
    }

    override fun apiResponse(responseData: ResponseData<Any>) {
        super.apiResponse(responseData)
        if (responseData.key == MOVIE_LIST_URL) {
            movieList.value = responseData.results as ArrayList<MovieModel>?
        }
    }

    fun setTitle(mBinding: FragmentHomeBinding, position: Int) {
        mBinding.txtTitle.text = movieList.value?.get(position)?.title ?: "Movie Title"
        val genre = StringBuilder("")
        for (movieModel in movieList.value?.get(position)?.genreIds ?: ArrayList<GenreModel>()) {
            if (genre.length > 1) {
                genre.append(", ")
            }
            genre.append(movieModel.name)
        }
        mBinding.txtGenre.text = genre.toString()
    }

    fun getSearchList(database: AppDatabase) {
        searchList = database.searchDao().getAll()
    }

    fun deleteSearch(database: AppDatabase, position: Int) {
        searchList.value?.get(position)?.let {
            database.searchDao().delete(it)
        }
    }

}