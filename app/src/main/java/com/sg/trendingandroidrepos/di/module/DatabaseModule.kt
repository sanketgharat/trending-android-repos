package com.sg.trendingandroidrepos.di.module

import dagger.Module
import androidx.annotation.NonNull

import javax.inject.Singleton

import androidx.room.Room

import android.app.Application
import com.sg.trendingandroidrepos.data.local.ReposDatabase
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import dagger.Provides


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ReposDatabase {
        return Room
            .databaseBuilder(
                application,
                ReposDatabase::class.java,
                "repos_db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubReposDao(database: ReposDatabase): GithubReposDao {
        return database.daoGithubRepos
    }
}