package com.sg.trendingandroidrepos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 4, exportSchema = false)
abstract class ReposDatabase : RoomDatabase() {
    abstract val daoGithubRepos: GithubReposDao
}

