package com.sg.trendingandroidrepos.di.module

import android.app.Application
import com.sg.trendingandroidrepos.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val myApp: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return myApp
    }
}