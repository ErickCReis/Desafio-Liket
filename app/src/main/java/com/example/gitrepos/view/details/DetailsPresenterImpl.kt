package com.example.gitrepos.view.details

import android.util.Log
import com.example.gitrepos.model.data.ItemsDatabase

class DetailsPresenterImpl(private val viewDetails: DetailsView,
                           private val itemId: Int,
                           private val database: ItemsDatabase): DetailsPresenter{

    override fun getData() {
        val item = database.itemsDao().getItem(itemId)
        viewDetails.loadDetails(item)
    }

}