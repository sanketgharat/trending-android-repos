package com.sg.trendingandroidrepos.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoOwner(

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "owner_id")
    val id: Int,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String
) : Parcelable {
    constructor() : this(0, "")
}
