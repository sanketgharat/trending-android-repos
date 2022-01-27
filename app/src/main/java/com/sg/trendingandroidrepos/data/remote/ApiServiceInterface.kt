package com.sg.trendingandroidrepos.data.remote

import com.sg.trendingandroidrepos.data.model.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    // https://api.github.com/search/repositories?sort=stars&order=desc&page=1&q=android&per_page=10

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int?,
        @Query("q") query: String?,
        @Query("per_page") perPage: Int?
    ): Response<GithubResponse?>

}