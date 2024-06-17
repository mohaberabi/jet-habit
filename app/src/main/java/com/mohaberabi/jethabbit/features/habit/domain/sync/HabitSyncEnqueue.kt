package com.mohaberabi.jethabbit.features.habit.domain.sync

import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel

interface HabitSyncEnqueue {


    suspend fun cancelAllSyncs()

    suspend fun enqueue(type: HabitSyncType)
}

sealed interface HabitSyncType {
    data class DeleteHabit(
        val habit: Habit,
        val uid: String,
    ) : HabitSyncType

    data class UpsertHabit(
        val habit: Habit,
        val uid: String,
    ) : HabitSyncType
}