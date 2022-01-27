package com.sg.trendingandroidrepos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sg.trendingandroidrepos.data.local.dao.GithubReposDao
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 3, exportSchema = false)
abstract class ReposDatabase : RoomDatabase() {
    abstract val daoGithubRepos: GithubReposDao
}

/*
private lateinit var INSTANCE: ReposDatabase

fun getDatabase(context: Context): ReposDatabase {
    synchronized(ReposDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    ReposDatabase::class.java,
                    "repos_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}*/
