package com.example.gitrepos.presenter


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitrepos.iterators.IItemIterator
import com.example.gitrepos.model.data.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ItemPresenter (val iterator: IItemIterator): IItemPresenter, ViewModel() {

    var items: MutableLiveData<MutableList<Item>> = MutableLiveData()

    init {
        loadItems()
    }

    override fun insert(data: MutableList<Item>) {
        iterator.insert(data)
    }

    override fun getAll(): LiveData<MutableList<Item>> {
        return items
    }

    fun loadItems() {
        iterator.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it -> items.postValue(it)
                },
                {
                    Log.d("ItemPresenter", "Fail")
                }
            )
    }

}