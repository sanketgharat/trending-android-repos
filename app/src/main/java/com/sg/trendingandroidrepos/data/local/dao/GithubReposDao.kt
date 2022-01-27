package com.sg.trendingandroidrepos.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity


@Dao
interface GithubReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GithubRepoEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiple(items: List<GithubRepoEntity>)

    @Update
    suspend fun update(item: GithubRepoEntity?)

    @Delete
    suspend fun delete(item: GithubRepoEntity?)

    @Query("DELETE FROM repos_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM repos_table")
    fun allRepoList(): LiveData<List<GithubRepoEntity>>
}