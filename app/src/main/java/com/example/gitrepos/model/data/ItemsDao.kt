package com.example.gitrepos.model.data

import androidx.room.*

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MutableList<Item>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: Item)

    @Query("SELECT * FROM item WHERE id == :id LIMIT 1")
    fun getItem(id: Int): Item

    @Query("SELECT * FROM item WHERE name == :name LIMIT 1")
    fun getItemId(name: String): Int

    @Query("SElECT * FROM item WHERE name LIKE :q ORDER BY stargazersCount DESC")
    fun getFilteredItems(q: String): MutableList<Item>

    @Query("SELECT * FROM item ORDER BY stargazersCount DESC")
    fun getAllItems(): MutableList<Item>
}