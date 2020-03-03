package com.example.gitrepos.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    @SerializedName("name")
    var name: String,
    @SerializedName("owner")
    var owner: Owner,
    @SerializedName("stargazers_count")
    var stargazersCount: Int
) : Parcelable