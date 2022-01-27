package com.sg.trendingandroidrepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sg.trendingandroidrepos.data.TrendingRepository
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.data.remote.ApiServiceInterface
import kotlinx.coroutines.*
import javax.inject.Inject

class ListingViewModel @Inject constructor(
    private val dao: GithubReposDao,
    private val apiServiceInterface: ApiServiceInterface
) : ViewModel() {

    private var repository: TrendingRepository? = null

    init {
        repository = TrendingRepository(dao, apiServiceInterface)
    }

    private val _repoList = MutableLiveData<List<GithubRepoEntity>>()
    val repoList: LiveData<List<GithubRepoEntity>>  get() = _repoList

    var job: Job? = null

    fun getRepositories(sort: String?,
                        order: String?,
                        page: Int?,
                        query: String?,
                        perPage: Int?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            repository?.let { trendingRepository ->
                val response = trendingRepository.getGithubRepos(sort, order, page, query, perPage)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        _repoList.postValue(response.body()!!.repositoryList)
                        //loading.value = false
                    } else {
                        //onError("Error : ${response.message()} ")
                    }
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}