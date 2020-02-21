package com.example.gitrepos.model

import com.google.gson.annotations.SerializedName

class Owner (
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("url")
    var url: String
)