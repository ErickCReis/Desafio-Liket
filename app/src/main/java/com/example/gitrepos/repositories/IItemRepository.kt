package com.example.gitrepos.repositories

import com.example.gitrepos.model.data.Item
import io.reactivex.Observable

interface IItemRepository {

    fun insert(data: MutableList<Item>)

    fun getAll(): Observable<MutableList<Item>>
}