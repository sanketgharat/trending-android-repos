package com.sg.trendingandroidrepos.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sg.trendingandroidrepos.databinding.ActivityRepoListBinding
import com.sg.trendingandroidrepos.ui.viewmodel.ListingViewModel
import com.sg.trendingandroidrepos.ui.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class RepoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoListBinding

    companion object{
        private const val TAG = "RepoListActivity"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ListingViewModel

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

     fun getIntentData() {

    }

     fun initViews() {

    }

     fun initObservers() {
        viewModel.repoList.observe(this, {
            Log.d(TAG, "initObservers: Repo List size ${it.size}")
        })
    }

     fun loadData() {
        viewModel.getRepositories("stars", "desc", 1, "android", 10)
    }


}