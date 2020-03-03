package com.example.gitrepos.view.home

import com.example.gitrepos.model.data.Item

interface HomeView {
    fun loadList(items: MutableList<Item>)
}