package com.mohaberabi.jethabbit.features.habit.domain.source.local

import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel
import com.mohaberabi.jethabbit.core.util.error.DataError

interface HabitSyncLocalDataSource {

    suspend fun insertHabitSync(pending: PendingHabitModel): EmptyDataResult<DataError>
    suspend fun deleteHabitSync(habitId: String): EmptyDataResult<DataError>
    suspend fun getAllPendingHabits(): AppResult<List<PendingHabitModel>, DataError>
    suspend fun deleteAllPendingHabits(): EmptyDataResult<DataError>
    suspend fun getPendingHabit(id: String): PendingHabitModel?
}


interface DeletedHabitLocalDataSource {
    suspend fun insertHabitSync(pending: PendingHabitModel): EmptyDataResult<DataError>
    suspend fun deleteHabitSync(habitId: String): EmptyDataResult<DataError>
    suspend fun getAllPendingDeletedHabits(): AppResult<List<PendingHabitModel>, DataError>
    suspend fun deleteAllDeletedHabits(): EmptyDataResult<DataError>
    suspend fun getDeletedHabit(id: String): PendingHabitModel?

}