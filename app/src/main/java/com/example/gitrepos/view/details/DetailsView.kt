package com.example.gitrepos.view.details

import com.example.gitrepos.model.data.Item

interface DetailsView {
    fun loadDetails(item: Item)
}