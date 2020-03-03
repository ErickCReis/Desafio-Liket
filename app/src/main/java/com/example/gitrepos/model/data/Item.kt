package com.example.gitrepos.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitrepos.model.Owner
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Item (
    @PrimaryKey var id: Int?,
    @SerializedName("name")
    var name: String,
    @SerializedName("owner")
    var owner: Owner,
    @SerializedName("stargazers_count")
    var stargazersCount: Int
) : Parcelable