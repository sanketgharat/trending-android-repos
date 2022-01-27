package com.sg.trendingandroidrepos.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepoOwner(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String
)
