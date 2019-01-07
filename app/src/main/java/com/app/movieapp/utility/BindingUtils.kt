package com.app.movieapp.utility

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("loadImage")
fun loadImageUrl(view: ImageView, url: String?) {

    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(view.context)
            .setDefaultRequestOptions(RequestOptions().placeholder(circularProgressDrawable).fitCenter()).load(url)
            .into(view)
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    if (isGone) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}








