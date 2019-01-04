package com.app.movieapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieModel(
    val id: String,
    val title: String,
    @Expose
    @SerializedName("genre_ids")
    val genreIds: ArrayList<GenreModel>,
    @Expose
    @SerializedName("age_category")
    val appCategory: String,
    val rate: String,
    @Expose()
    @SerializedName("release_date")
    val releaseDate: String,
    @Expose()
    @SerializedName("poster_path")
    var posterPath: String,
    @Expose()
    @SerializedName("presale_flag")
    val preSaleFlag: Int
)