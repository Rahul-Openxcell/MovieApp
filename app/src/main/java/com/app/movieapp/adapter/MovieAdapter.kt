package com.app.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseViewHolder
import com.app.movieapp.databinding.ItemMovieDetailsBinding
import com.app.movieapp.databinding.NetworkStateItemBinding
import com.app.movieapp.model.MovieModel
import com.app.movieapp.retrofit.NetworkState

class MovieAdapter(private val retryCallBack: () -> Unit) : PagedListAdapter<MovieModel, MovieAdapter.HomeItemHolder>(MovieModel.movieDiffUtil) {

    private lateinit var context: Context
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: View? = null
        return when (viewType) {
            R.layout.item_movie_details -> {
                view = layoutInflater.inflate(R.layout.item_movie_details, parent, false)
                HomeItemHolder(view)
            }

            R.layout.network_state_item -> {
                view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
                HomeItemHolder(view)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie_details -> {
                val itemBinding: ItemMovieDetailsBinding = holder.getBinding() as ItemMovieDetailsBinding
                itemBinding.movieModel = getItem(position)
                itemBinding.executePendingBindings()
            }


            R.layout.network_state_item -> {
                val networkStateItemBinding: com.app.movieapp.databinding.NetworkStateItemBinding = holder.getBinding() as NetworkStateItemBinding
                networkStateItemBinding.networkState = networkState
                networkStateItemBinding.retryButton.setOnClickListener {
                    retryCallBack()
                }
            }

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_movie_details
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    class HomeItemHolder(view: View) : BaseViewHolder(view)
}