package com.example.gitrepos.view.details

import com.example.gitrepos.model.item.ItemsDatabase

class DetailsPresenterImpl(private val viewDetails: DetailsView,
                           private val itemId: Int,
                           private val database: ItemsDatabase): DetailsPresenter{

    override fun getData() {
        val item = database.itemsDao().getItem(itemId)
        viewDetails.loadDetails(item)
    }

}