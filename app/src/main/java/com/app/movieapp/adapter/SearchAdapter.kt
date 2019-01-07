package com.app.movieapp.adapter

import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseViewHolder
import com.app.movieapp.databinding.ItemSearchBinding
import com.app.movieapp.db.SearchEntity

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.HomeItemHolder>() {

    private val searchList = ArrayList<SearchEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return HomeItemHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        val itemBinding: ItemSearchBinding = holder.getBinding() as ItemSearchBinding
        itemBinding.searchModel = searchList[position]
        itemBinding.executePendingBindings()
    }

    fun updateList(list: List<SearchEntity>) {
        val diffCallback = SearchEntity.Companion.SearchDiffCallback(list, this.searchList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.searchList.clear()
        this.searchList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeAt(position: Int) {
        searchList.removeAt(position)
        notifyItemRemoved(position)
    }


    class HomeItemHolder(view: View) : BaseViewHolder(view)
}