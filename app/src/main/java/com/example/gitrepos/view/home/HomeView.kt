package com.example.gitrepos.view.home

import com.example.gitrepos.model.item.Item

interface HomeView {
    fun loadList(items: MutableList<Item>)
}