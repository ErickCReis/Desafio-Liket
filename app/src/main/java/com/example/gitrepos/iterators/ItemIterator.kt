package com.example.gitrepos.iterators


import com.example.gitrepos.model.data.Item
import com.example.gitrepos.repositories.ItemRepository
import io.reactivex.Observable

class ItemIterator (val repository: ItemRepository): IItemIterator {
    override fun insert(data: MutableList<Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<MutableList<Item>> {
        return repository.getAll()
    }
}