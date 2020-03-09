package com.example.gitrepos.model.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Profile (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    @SerializedName("name")
    var name: String,
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("html_url")
    var link: String
) : Parcelable