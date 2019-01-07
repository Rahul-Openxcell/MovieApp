package com.app.movieapp.retrofit

import com.app.movieapp.model.MovieListModel
import com.app.movieapp.model.MovieModel
import com.app.movieapp.model.ResponseData
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Rahul Sadhu
 */
const val MOVIE_LIST_URL = "home"
const val MOVIE_SEARCH_URL = "search"


interface ApiService {

    @GET(MOVIE_LIST_URL)
    fun movieList(): Call<ResponseData<ArrayList<MovieModel>>>

    @GET(MOVIE_SEARCH_URL)
    fun movieSearchList(
            @Query("keyword") keyWord: String,
            @Query("offset") offset: Int,
            @Query("type") type: String
    ): Call<ResponseData<MovieListModel>>

}
