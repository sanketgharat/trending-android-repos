package com.sg.trendingandroidrepos.data.local.dao

import androidx.room.*
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity


@Dao
interface GithubReposDao {

    @Insert
    suspend fun insert(item: GithubRepoEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiple(items: List<GithubRepoEntity>)

    @Update
    suspend fun update(item: GithubRepoEntity?)

    @Delete
    suspend fun delete(item: GithubRepoEntity?)
}