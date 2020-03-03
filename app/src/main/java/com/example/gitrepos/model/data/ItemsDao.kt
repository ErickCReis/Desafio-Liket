package com.example.gitrepos.model.data

import androidx.room.*

@Dao
interface ItemsDao {

    @Insert
    fun insert(item: MutableList<Item>)

    @Update
    fun update(item: MutableList<Item>)

    @Query("SELECT * FROM item ORDER BY id DESC")
    fun getAllItems(): List<Item>
}