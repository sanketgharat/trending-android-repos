package com.sg.trendingandroidrepos.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity

data class GithubResponse(
    @SerializedName("total_count")
    @Expose
    var totalCount :String,

    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults :String,

    @SerializedName("items")
    @Expose
    var repositoryList :List<GithubRepoEntity>
    )

