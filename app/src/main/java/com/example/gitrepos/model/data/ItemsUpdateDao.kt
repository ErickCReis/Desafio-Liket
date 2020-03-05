package com.example.gitrepos.model.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ItemsUpdateDao {

    @Insert
    fun insert(item: MutableList<Item>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: Item): Completable

    @Query("SELECT * fROM item WHERE id == :id LIMIT 1")
    fun getItem(id: Int): Item

    @Query("SELECT * fROM item WHERE name == :name LIMIT 1")
    fun getItemId(name: String): Int

    @Query("SELECT * FROM item ORDER BY stargazersCount DESC")
    fun getAllItems(): Observable<MutableList<Item>>
}