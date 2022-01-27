package com.sg.trendingandroidrepos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.sg.trendingandroidrepos.R
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.databinding.ActivityRepoListBinding
import com.sg.trendingandroidrepos.ui.adapter.RepoListAdapter
import com.sg.trendingandroidrepos.ui.viewmodel.ListingViewModel
import com.sg.trendingandroidrepos.ui.viewmodel.ViewModelFactory
import com.sg.trendingandroidrepos.utils.CommonUtils
import com.sg.trendingandroidrepos.utils.Constants
import com.sg.trendingandroidrepos.utils.RepoSyncWorker
import dagger.android.AndroidInjection
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RepoListActivity : BaseActivity(), RepoListAdapter.OnRepoSelectionListener {

    companion object {
        private const val TAG = "RepoListActivity"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityRepoListBinding
    private lateinit var viewModel: ListingViewModel
    lateinit var adapterRepoList: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListingViewModel::class.java]

        initViews()
        initObservers()
        loadData()
    }

    override fun getIntentData() {

    }

    override fun initViews() {
        initRecycler()
        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d(TAG, "initViews: setOnRefreshListener")
            if (CommonUtils.isOnline(applicationContext)) {
                viewModel.getRepositories("stars", "desc", 1, "android", 20)
            } else {
                showToast(getString(R.string.check_internet_message))
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecycler() {
        adapterRepoList = RepoListAdapter()
        adapterRepoList.setOnRepoSelectionListener(this)
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapterRepoList
    }

    override fun initObservers() {
        viewModel.repoList.observe(this, {
            Log.d(TAG, "initObservers: repoList size ${it.size}")
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.getAllReposList().observe(this, { listOfRepos ->
            Log.d(TAG, "initObservers: getAllReposList")
            if (listOfRepos != null) {
                Log.d(TAG, "initObservers: getAllReposList ${listOfRepos.size}")
                if (listOfRepos.isNotEmpty()) {
                    binding.textViewError.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.VISIBLE
                }
                adapterRepoList.setList(listOfRepos)
            } else {
                adapterRepoList.setList(emptyList())
            }
        })

        viewModel.loadingRepos.observe(this, { isLoading ->
            if (isLoading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })
        viewModel.errorMessage.observe(this, { errorMessage ->
            if (errorMessage.isNotBlank())
                showToast(errorMessage)
        })
    }

    override fun loadData() {
        //viewModel.getRepositories("stars", "desc", 1, "android", 20)
        initWorkManagerTask()
    }

    override fun onRepoSelected(item: GithubRepoEntity) {
        Log.d(TAG, "onRepoSelected: ${item.name}")

        val intent = Intent(this, RepoDetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_REPO, item)
        startActivity(intent)
    }

    private fun initWorkManagerTask() {
        runBlocking {
            if (RepoSyncWorker.isScheduled(applicationContext)) {
                Log.d(TAG, "initWorkManagerTask: isScheduled : true")
            } else {
                Log.d(TAG, "initWorkManagerTask: isScheduled : false")
                val constraints: Constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                /*val repoSyncWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<RepoSyncWorker>()
                        .setConstraints(constraints)
                        .addTag(Constants.WORK_SYNC_REPO_TAG)
                        .build()*/

                //Repeat work every 15 minutes
                val repoSyncWorkRequest =
                    PeriodicWorkRequestBuilder<RepoSyncWorker>(15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag(Constants.WORK_SYNC_REPO_TAG)
                        .build()


                WorkManager
                    .getInstance(this@RepoListActivity)
                    .enqueueUniquePeriodicWork(
                        Constants.WORK_SYNC_REPO_NAME,
                        ExistingPeriodicWorkPolicy.REPLACE,
                        repoSyncWorkRequest
                    )
            }
        }

    }

    private fun deleteAllRepos(){
        viewModel.deleteAllGithubRepos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_listing, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_delete -> {
                Log.d(TAG, "onOptionsItemSelected: action_delete")
                    deleteAllRepos()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}