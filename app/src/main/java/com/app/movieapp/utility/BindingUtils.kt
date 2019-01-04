package com.app.movieapp.utility

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.app.movieapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


/**
 * Created by RahulSadhu.
 */


@BindingAdapter("loadCornerImage")
fun loadCornerImage(view: ImageView, url: String?) {
    Glide.with(view.context).setDefaultRequestOptions(
        RequestOptions().transform(
            MultiTransformation(
                CenterCrop(),
                RoundedCorners(8)
            )
        ).placeholder(R.drawable.ic_placeholder)
    ).load(url).into(view)
}

@BindingAdapter("loadImage")
fun loadImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_placeholder)).load(url)
        .into(view)
}

@BindingAdapter("loadCircleImage")
fun loadCircleImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .setDefaultRequestOptions(RequestOptions().transform(CircleCrop()).placeholder(R.drawable.ic_placeholder))
        .load(url).into(view)
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    if (isGone) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}








