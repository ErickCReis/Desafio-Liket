package com.example.gitrepos.model

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("name")
    var name: String,
    @SerializedName("owner")
    var owner: Owner,
    @SerializedName("stargazers_count")
    var stargazersCount: Int
)