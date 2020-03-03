package com.example.gitrepos.view.home

import com.example.gitrepos.model.Data.Items
import com.example.gitrepos.model.Item

interface HomeView {
    fun loadList(items: MutableList<Item>)

    fun saveList(items: Items)
}