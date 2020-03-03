package com.example.gitrepos.model.Data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Items) : Completable

    @Query("SELECT * FROM items_table ORDER BY id DESC")
    fun getAllQuotes(): Observable<List<Items>>
}