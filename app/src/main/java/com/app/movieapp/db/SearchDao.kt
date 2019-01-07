package com.app.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchDao {

    @Insert
    fun insert(searchEntity: SearchEntity)

    @Query("select * from search order by search_id DESC limit 10")
    fun getAll(): LiveData<List<SearchEntity>>
}