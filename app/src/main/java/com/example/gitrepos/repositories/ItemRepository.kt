package com.example.gitrepos.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.gitrepos.model.data.Item
import com.example.gitrepos.model.data.ItemsUpdateDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ItemRepository(private val itemDao: ItemsUpdateDao) : IItemRepository {

    @SuppressLint("CheckResult")
    override fun insert(data: MutableList<Item>) {
        itemDao.insert(data)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("ItemRepository", "Success")
                },
                {
                    Log.d("ItemRepository", "Fail")
                }
            )
    }

    override fun getAll(): Observable<MutableList<Item>> {
        return itemDao.getAllItems()
    }
}