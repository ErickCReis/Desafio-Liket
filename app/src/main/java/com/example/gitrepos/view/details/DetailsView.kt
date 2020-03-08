package com.example.gitrepos.view.details

import com.example.gitrepos.model.item.Item

interface DetailsView {
    fun loadDetails(item: Item)
}