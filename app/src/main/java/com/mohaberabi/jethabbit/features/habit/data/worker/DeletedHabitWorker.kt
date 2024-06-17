package com.mohaberabi.jethabbit.features.habit.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.features.habit.domain.source.local.DeletedHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.remote.HabitRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

@HiltWorker
class DeletedHabitWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val params: WorkerParameters,
    private val deletedHabitLocalDataSource: DeletedHabitLocalDataSource,
    private val habitRemoteDataSource: HabitRemoteDataSource,
    private val dispatchers: DispatchersProvider,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(dispatchers.io) {
            try {


                if (runAttemptCount >= 5) {
                    Result.failure()
                } else {

                    val deletedId = params.inputData.getString(WorkerConst.HABIT_ID)
                        ?: return@withContext Result.failure()

                    val deletedHabit =
                        deletedHabitLocalDataSource.getDeletedHabit(deletedId)
                            ?: return@withContext Result.failure()
                    supervisorScope {

                        val remoteDeferred = async {
                            habitRemoteDataSource.deleteHabit(
                                id = deletedId,
                                uid = deletedHabit.uid
                            )
                        }

                        val res = remoteDeferred.await()
                        when (res) {
                            is AppResult.Done -> {
                                launch {
                                    deletedHabitLocalDataSource.deleteHabitSync(deletedId)
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