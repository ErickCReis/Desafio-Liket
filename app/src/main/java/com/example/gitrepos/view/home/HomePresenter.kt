package com.example.gitrepos.view.home

import com.example.gitrepos.model.item.Item

interface HomePresenter {
    fun getData()
    fun showData()
    fun showFilteredData(items: MutableList<Item>)
}