package com.example.gitrepos.model

import com.google.gson.annotations.SerializedName

data class Repositories (
    @SerializedName("total_count")
    var totalCount: Int,
    @SerializedName("items")
    var items: MutableList<Item>
)
