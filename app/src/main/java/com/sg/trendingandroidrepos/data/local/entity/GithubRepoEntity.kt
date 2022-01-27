package com.sg.trendingandroidrepos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos_table")
public data class GithubRepoEntity(
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

    /*@SerializedName("owner")
    @Expose
    val owner: RepoOwner*/

){
    constructor() : this(0, "", "", "", false, ""){

    }
}
