package com.mohaberabi.jethabbit.features.habit.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.mohaberabi.jethabbit.core.di.ApplicationScope
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.features.habit.data.worker.DeletedHabitWorker
import com.mohaberabi.jethabbit.features.habit.data.worker.PendingHabitWorker
import com.mohaberabi.jethabbit.features.habit.data.worker.WorkerConst
import com.mohaberabi.jethabbit.features.habit.domain.source.local.DeletedHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitSyncLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.sync.HabitSyncEnqueue
import com.mohaberabi.jethabbit.features.habit.domain.sync.HabitSyncType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultHabitSyncEnqueuer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: DispatchersProvider,
    private val deletedHabitLocalDataSource: DeletedHabitLocalDataSource,
    private val habitSyncLocalDataSource: HabitSyncLocalDataSource,
    @ApplicationScope private val appScope: CoroutineScope
) : HabitSyncEnqueue {
    private val workManager = context.getSystemService(WorkManager::class.java)
    override suspend fun cancelAllSyncs() {
        withContext(dispatchers.io) {
            try {
                appScope.launch {
                    workManager.cancelAllWorkByTag(WorkerConst.DELETE_WORKER_TAG)
                }.join()
                appScope.launch {
                    workManager.cancelAllWorkByTag(WorkerConst.UPSERT_WORKER_TAG)
                }.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun enqueue(type: HabitSyncType) {

        when (type) {
            is HabitSyncType.DeleteHabit -> enqueueDeleteHabit(
                uid = type.uid,
                habit = type.habit,
            )

            is HabitSyncType.UpsertHabit -> enqueueUpsertHabit(
                uid = type.uid,
                habit = type.habit
            )
        }

    }

    private suspend fun enqueueUpsertHabit(
        uid: String,
        habit: Habit
    ) {
        withContext(dispatchers.io) {
            val insertedToBeAdded = PendingHabitModel(
                habitId = habit.id,
                uid = uid,
                habit = habit
            )
            habitSyncLocalDataSource.insertHabitSync(insertedToBeAdded)
            val upsertHabitRequest = workManager.defaultOneTimeRequest<PendingHabitWorker>(
                tag = WorkerConst.UPSERT_WORKER_TAG,
                inputs = Data.Builder().putString(WorkerConst.HABIT_ID, habit.id)
            ).build()

            appScope.launch {
                workManager.enqueue(upsertHabitRequest).await()
            }.join()

        }
    }


    private suspend fun enqueueDeleteHabit(
        uid: String,
        habit: Habit
    ) {
        withContext(dispatchers.io) {
            val insertedToBeDeleted = PendingHabitModel(
                habitId = habit.id,
                uid = uid,
                habit = habit
            )
            deletedHabitLocalDataSource.insertHabitSync(insertedToBeDeleted)
            val deletedWorkerRequest = workManager.defaultOneTimeRequest<DeletedHabitWorker>(
                tag = WorkerConst.DELETE_WORKER_TAG,
                inputs = Data.Builder().putString(WorkerConst.HABIT_ID, habit.id)
            ).build()

            appScope.launch {
                workManager.enqueue(deletedWorkerRequest).await()
            }.join()

        }
    }


}

private inline fun <reified T : ListenableWorker> WorkManager.defaultOneTimeRequest(
    tag: String,
    inputs: Data.Builder,
): OneTimeWorkRequest.Builder {
    val request = OneTimeWorkRequestBuilder<T>()
        .addTag(tag)
        .setConstraints(WorkerConst.baseConstraints)
        .setBackoffCriteria(
            backoffPolicy = WorkerConst.RETRY_POLICY,
            backoffDelay = WorkerConst.BACK_OFF_DELAY,
            timeUnit = WorkerConst.BACK_OFF_TIME_UNIT
        ).setInputData(
            inputs.build()
        )

    return request
}