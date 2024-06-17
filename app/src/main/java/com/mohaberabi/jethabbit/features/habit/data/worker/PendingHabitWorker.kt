package com.mohaberabi.jethabbit.features.habit.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitSyncLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.remote.HabitRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext


@HiltWorker
class PendingHabitWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val params: WorkerParameters,
    private val habitSyncDataSource: HabitSyncLocalDataSource,
    private val habitRemoteDataSource: HabitRemoteDataSource,
    private val dispatchers: DispatchersProvider,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(dispatchers.io) {
            try {
                if (runAttemptCount >= 5) {
                    Result.failure()
                } else {
                    val pendingId =
                        params.inputData.getString(WorkerConst.HABIT_ID)
                            ?: return@withContext Result.failure()
                    val pendingHabit =
                        habitSyncDataSource.getPendingHabit(pendingId)
                            ?: return@withContext Result.failure()
                    supervisorScope {
                        val remoteDeferred = async {
                            habitRemoteDataSource.addHabit(
                                habit = pendingHabit.habit,
                                uid = pendingHabit.uid
                            )
                        }
                        val res = remoteDeferred.await()
                        when (res) {
                            is AppResult.Done -> {
                                launch {
                                    habitSyncDataSource.deleteHabitSync(pendingId)
                                }.join()
                                Result.success()
                            }

                            is AppResult.Error -> Result.retry()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }
        }
    }
}