package com.sg.trendingandroidrepos.utils

import android.content.Context
import android.util.Log
import androidx.work.*
import com.sg.trendingandroidrepos.MyApp
import com.sg.trendingandroidrepos.data.TrendingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoSyncWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(
    context,
    workerParams
) {

    companion object {
        private const val TAG = "RepoSyncWorker"

        suspend fun isScheduled(context: Context): Boolean {
            val workManager = WorkManager.getInstance(context)

            val workInfoList =
                workManager.getWorkInfosForUniqueWork(Constants.WORK_SYNC_REPO_NAME).await()

            if (workInfoList.size == 1) {
                Log.d(TAG, "isScheduled: found")
                val workInfo = workInfoList[0]
                if (workInfo.state == WorkInfo.State.BLOCKED || workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING) {
                    Log.d(TAG, "isScheduled: Running")
                    return true
                } else {
                    Log.d(TAG, "isScheduled: Done")
                }
            } else {
                Log.d(TAG, "isScheduled: not found")
            }

            return false
        }

    }

    @Inject
    lateinit var trendingRepository: TrendingRepository

    init {
        (applicationContext as MyApp).component.injectWorker(this)
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: working")
        Log.d(TAG, "doWork: working thread :${Thread.currentThread().name}")

        return try {

            val response = trendingRepository.getGithubRepos("stars", "desc", 1, "android", 20)
            if (response.isSuccessful && response.body() != null) {
                Log.d(
                    TAG,
                    "getRepositories: isSuccessful true ${response.body()!!.repositoryList.size}"
                )

                CoroutineScope(IO).launch {
                    trendingRepository.insertGithubRepoList(response.body()!!.repositoryList)
                }

                Result.success()
            } else {
                Log.d(TAG, "getRepositories: isSuccessful false")
                Result.failure()
            }


        } catch (ex: Exception) {
            Log.e(TAG, "doWork: Exception", ex)
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }

    }

}