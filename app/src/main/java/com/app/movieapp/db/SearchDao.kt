package com.app.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchEntity: SearchEntity)

    @Query("select * from search order by search_id DESC limit 10")
    fun getAll(): LiveData<List<SearchEntity>>

    @Delete
    fun delete(searchEntity: SearchEntity)
}