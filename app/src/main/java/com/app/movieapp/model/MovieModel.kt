package com.app.movieapp.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
data class MovieModel(
        var id: String,
        var title: String,
        @Expose
        @SerializedName("genre_ids")
        var genreIds: ArrayList<GenreModel>,
        @Expose
        @SerializedName("age_category")
        var appCategory: String,
        var rate: String,
        @Expose()
        @SerializedName("release_date")
        var releaseDate: Long,
        @Expose()
        @SerializedName("poster_path")
        var posterPath: String,
        @Expose()
        @SerializedName("presale_flag")
        var preSaleFlag: Int,
        var description: String
) : Parcelable {

    constructor() : this("", "", ArrayList<GenreModel>(), "", "", System.currentTimeMillis(), "", 0, "")

    companion object {
        val movieDiffUtil = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean = oldItem.id == newItem.id


        }
    }


    fun getRating(): Float {
        return rate.toFloat() / 2
    }

    fun displayDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = releaseDate
        return formatter.format(calendar.time)
    }

}