package com.sg.trendingandroidrepos.ui.activity

import android.os.Bundle
import com.bumptech.glide.Glide
import com.sg.trendingandroidrepos.R
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.databinding.ActivityRepoDetailBinding
import com.sg.trendingandroidrepos.utils.Constants

class RepoDetailActivity : BaseActivity() {

    lateinit var binding: ActivityRepoDetailBinding
    private var data: GithubRepoEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        initViews()
        initObservers()
        loadData()
    }

    override fun getIntentData() {
        if(intent.hasExtra(Constants.EXTRA_REPO)){
            data = intent.getParcelableExtra(Constants.EXTRA_REPO)
        }
    }

    override fun initViews() {

    }

    override fun initObservers() {

    }

    override fun loadData() {
        data?.let {
            binding.textViewName.text = it.fullName
            binding.textViewDescription.text = it.description

            Glide.with(this).load(it.owner.avatarUrl)
                .error(R.mipmap.ic_launcher_round)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(binding.imageOwner)
        }
    }

}