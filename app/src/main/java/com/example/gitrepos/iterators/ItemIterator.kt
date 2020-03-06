package com.example.gitrepos.iterators

import com.example.gitrepos.model.data.Item
import com.example.gitrepos.repositories.ItemRepository
import io.reactivex.Observable

class ItemIterator (private val repository: ItemRepository): IItemIterator {
    override fun insert(data: MutableList<Item>) {
        repository.insert(data)
    }

    override fun getAll(): Observable<MutableList<Item>> {
        return repository.getAll()
    }
}