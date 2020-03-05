package com.example.gitrepos.iterators


import com.example.gitrepos.model.data.Item
import io.reactivex.Observable

interface IItemIterator {

    fun insert(data: MutableList<Item>)

    fun getAll(): Observable<MutableList<Item>>
}