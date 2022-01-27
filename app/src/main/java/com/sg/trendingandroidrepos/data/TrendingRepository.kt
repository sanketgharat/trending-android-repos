package com.sg.trendingandroidrepos.data

import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.remote.ApiServiceInterface
import retrofit2.http.Query

class TrendingRepository(
    private val githubReposDao: GithubReposDao,
    private val githubApiService: ApiServiceInterface
) {

    suspend fun getGithubRepos(
        sort: String?,
        order: String?,
        page: Int?,
        query: String?,
        perPage: Int?
    ) = githubApiService.getRepos(sort, order, page, query, perPage
    )
}