package com.app.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.movieapp.R
import com.app.movieapp.baseclass.BaseViewHolder
import com.app.movieapp.databinding.ItemSearchBinding
import com.app.movieapp.db.SearchEntity
import com.app.movieapp.utility.KEY_KEYWORD
import com.app.movieapp.utility.Utils
import com.app.movieapp.views.activity.MovieActivity

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.HomeItemHolder>() {

    val searchList = ArrayList<SearchEntity>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        return HomeItemHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        val itemBinding: ItemSearchBinding = holder.getBinding() as ItemSearchBinding
        itemBinding.searchModel = searchList[position]
        itemBinding.root.setOnClickListener {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(KEY_KEYWORD, searchList[holder.adapterPosition].name)
            Utils.startNewActivity(context, intent)
        }
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