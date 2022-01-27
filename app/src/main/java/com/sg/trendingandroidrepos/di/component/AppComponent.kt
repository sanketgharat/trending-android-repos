package com.sg.trendingandroidrepos.di.component

import com.sg.trendingandroidrepos.MyApp
import dagger.Component
import javax.inject.Singleton
import android.app.Application
import com.sg.trendingandroidrepos.di.module.*

import dagger.BindsInstance
import dagger.android.AndroidInjectionModule

@Singleton
@Component(
    modules =
    [
        AndroidInjectionModule::class,
        ApiClientModule::class,
        ListActivityModule::class,
        DatabaseModule::class,
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
}
