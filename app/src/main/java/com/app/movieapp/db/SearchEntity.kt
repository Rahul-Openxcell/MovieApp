package com.app.movieapp.db

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search", indices = [Index(value = ["search_name"], unique = true)])
data class SearchEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "search_id")
        val id: Int = 0,
        @ColumnInfo(name = "search_name")
        var name: String
) {

    companion object {
        class SearchDiffCallback(private var newList: List<SearchEntity>, private var oldList: List<SearchEntity>) : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] === newList[newItemPosition]
            }

        }
    }
}