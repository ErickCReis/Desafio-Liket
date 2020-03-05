package com.example.gitrepos.presenter


import androidx.lifecycle.LiveData
import com.example.gitrepos.model.data.Item
import io.reactivex.Observable

interface IItemPresenter {

    fun insert(data: MutableList<Item>)

    fun getAll(): LiveData<MutableList<Item>>
}