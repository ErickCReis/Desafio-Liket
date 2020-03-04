package com.example.gitrepos.model.data

import androidx.room.*

@Dao
interface ItemsDao {

    @Insert
    fun insert(item: MutableList<Item>)

    @Update
    fun update(item: Item)

    @Query("SELECT * fROM item WHERE id == :id LIMIT 1")
    fun getItem(id: Int): Item

    @Query("SELECT * fROM item WHERE name == :name LIMIT 1")
    fun getItemId(name: String): Int

    @Query("SELECT * FROM item ORDER BY stargazersCount DESC")
    fun getAllItems(): MutableList<Item>
}