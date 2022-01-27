package com.sg.trendingandroidrepos.di.component

import com.sg.trendingandroidrepos.MyApp
import dagger.Component
import javax.inject.Singleton
import android.app.Application
import com.sg.trendingandroidrepos.data.TrendingRepository
import com.sg.trendingandroidrepos.di.module.*
import com.sg.trendingandroidrepos.utils.RepoSyncWorker

import dagger.BindsInstance
import dagger.android.AndroidInjectionModule

@Singleton
@Component(
    modules =
    [
        AndroidInjectionModule::class,
        DatabaseModule::class,
        ApiClientModule::class,
        ListingViewModelModule::class,
        ListingActivityModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: MyApp)
    fun injectWorker(repository: RepoSyncWorker)
}
