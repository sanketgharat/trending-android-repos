package com.sg.trendingandroidrepos.data

import androidx.lifecycle.LiveData
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.data.remote.ApiServiceInterface
import retrofit2.http.Query
import javax.inject.Inject

class TrendingRepository @Inject constructor (
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

    fun getAllReposList(): LiveData<List<GithubRepoEntity>> {
        return githubReposDao.allRepoList()
    }

    suspend fun insertGithubRepoList(list: List<GithubRepoEntity>){
        githubReposDao.insertMultiple(list)
    }

    suspend fun deleteAllGithubRepos(){
        githubReposDao.deleteAll()
    }
}