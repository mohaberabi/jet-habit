package com.mohaberabi.jethabbit.features.habit.data.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import java.util.concurrent.TimeUnit

object WorkerConst {


    const val HABIT_ID = "habitId"
    val RETRY_POLICY = BackoffPolicy.EXPONENTIAL
    const val BACK_OFF_DELAY = 2000L
    val BACK_OFF_TIME_UNIT = TimeUnit.MILLISECONDS
    val baseConstraints: Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()
    const val DELETE_WORKER_TAG = "deleteWorkerTag"
    const val UPSERT_WORKER_TAG = "upsertWorkerTage"

}