package com.sg.trendingandroidrepos.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.trendingandroidrepos.data.TrendingRepository
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.data.remote.ApiServiceInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListingViewModel @Inject constructor(
    private val dao: GithubReposDao,
    private val apiServiceInterface: ApiServiceInterface
) : ViewModel() {

    companion object {
        private const val TAG = "ListingViewModel"
    }

    private var repository: TrendingRepository = TrendingRepository(dao, apiServiceInterface)

    var job: Job? = null
    private val _repoList = MutableLiveData<List<GithubRepoEntity>>()
    val repoList: LiveData<List<GithubRepoEntity>> get() = _repoList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loadingRepos = MutableLiveData<Boolean>()
    val loadingRepos: LiveData<Boolean> get() = _loadingRepos

    fun getRepositories(
        sort: String?,
        order: String?,
        page: Int?,
        query: String?,
        perPage: Int?
    ) {

        Log.d(TAG, "getRepositories: call")

        _loadingRepos.value = true

        job = viewModelScope.launch(IO) {
            repository.let { trendingRepository ->
                val response = trendingRepository.getGithubRepos(sort, order, page, query, perPage)

                withContext(Main) {
                    _loadingRepos.value = false
                    if (response.isSuccessful && response.body() != null) {
                        Log.d(TAG, "getRepositories: isSuccessful true")
                        _repoList.postValue(response.body()!!.repositoryList)
                        withContext(IO) {
                            repository.insertGithubRepoList(response.body()!!.repositoryList)
                        }
                    } else {
                        Log.d(TAG, "getRepositories: isSuccessful false")
                        _errorMessage.value = "Error: ${response.message()}"
                    }
                }

            }

        }
    }

    fun getAllReposList(): LiveData<List<GithubRepoEntity>> {
        return repository.getAllReposList()
    }

    fun deleteAllGithubRepos() = viewModelScope.launch(IO) {
        repository.deleteAllGithubRepos()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}