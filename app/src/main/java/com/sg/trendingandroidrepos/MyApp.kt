package com.sg.trendingandroidrepos

import android.app.Application
import com.sg.trendingandroidrepos.di.component.AppComponent
import com.sg.trendingandroidrepos.di.component.DaggerAppComponent
import com.sg.trendingandroidrepos.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var mInjector : DispatchingAndroidInjector<Any>

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .application(this)
            .build()

        component.inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInjector
    }
}