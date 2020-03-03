package com.example.gitrepos.model

import com.example.gitrepos.model.data.Item
import com.google.gson.annotations.SerializedName

data class Repositories (
    @SerializedName("total_count")
    var totalCount: Int,
    @SerializedName("items")
    var items: MutableList<Item>
)
