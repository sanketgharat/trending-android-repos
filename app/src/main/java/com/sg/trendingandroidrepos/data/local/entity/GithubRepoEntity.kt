package com.sg.trendingandroidrepos.data.local.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "repos_table")
data class GithubRepoEntity(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("node_id")
    @Expose
    var nodeId: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("full_name")
    @Expose
    var fullName: String,

    @SerializedName("private")
    @Expose
    var private: Boolean,

    @SerializedName("description")
    @Expose
    var description: String,

    @SerializedName("owner")
    @Expose
    @Embedded
    var owner: RepoOwner

) : Parcelable {
    constructor() : this(0, "", "", "", false, "", RepoOwner()) {

    }

}
