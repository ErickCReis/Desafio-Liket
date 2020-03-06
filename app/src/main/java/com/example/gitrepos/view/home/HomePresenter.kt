package com.example.gitrepos.view.home

import androidx.lifecycle.LifecycleOwner
import com.example.gitrepos.model.data.Item
import com.example.gitrepos.presenter.ItemPresenter

interface HomePresenter {
    fun getData()
    fun showData()
    fun showFilteredData(items: MutableList<Item>)
    fun setView(itemPresenter: ItemPresenter, owner: LifecycleOwner)
}