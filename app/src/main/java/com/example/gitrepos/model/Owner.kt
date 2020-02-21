package com.example.gitrepos.model

import android.media.Image
import com.google.gson.annotations.SerializedName

data class Owner (
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("url")
    var url: String,
    var avatar: Image
)