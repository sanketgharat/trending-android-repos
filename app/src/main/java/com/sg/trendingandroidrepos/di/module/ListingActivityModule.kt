package com.sg.trendingandroidrepos.di.module

import com.sg.trendingandroidrepos.ui.activity.BaseActivity
import com.sg.trendingandroidrepos.ui.activity.RepoListActivity
import com.sg.trendingandroidrepos.ui.activity.RepoListActivity_MembersInjector
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ListingActivityModule {

    /*@ContributesAndroidInjector
    abstract fun contributeBaseActivity(): BaseActivity*/

    /*@ContributesAndroidInjector
    abstract fun contributeRepoListActivityM(): RepoListActivity_MembersInjector*/

    @ContributesAndroidInjector
    abstract fun contributeRepoListActivity(): RepoListActivity
}