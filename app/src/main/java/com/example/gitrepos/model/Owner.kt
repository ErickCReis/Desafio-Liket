package com.example.gitrepos.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Owner (
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    var avatar: Bitmap
)