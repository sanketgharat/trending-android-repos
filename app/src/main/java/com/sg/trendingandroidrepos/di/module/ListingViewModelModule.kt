package com.sg.trendingandroidrepos.di.module

import dagger.Module
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sg.trendingandroidrepos.ui.viewmodel.ListingViewModel
import com.sg.trendingandroidrepos.ui.viewmodel.ViewModelFactory

import dagger.multibindings.IntoMap

import dagger.Binds


@Module
abstract class ListingViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    protected abstract fun listingViewModel(viewModel: ListingViewModel): ViewModel
}