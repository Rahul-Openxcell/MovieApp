package com.app.movieapp.retrofit

import com.app.movieapp.model.MovieModel
import com.app.movieapp.model.ResponseData
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Rahul Sadhu
 */
const val MOVIE_LIST_URL = "home"


interface ApiService {

    @GET(MOVIE_LIST_URL)
    fun movieList(): Call<ResponseData<ArrayList<MovieModel>>>

}
