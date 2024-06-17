package com.mohaberabi.jethabbit.features.habit.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jethabbit.core.data.database.dao.HabitSyncDao
import com.mohaberabi.jethabbit.core.data.database.entity.toDeletedEntity
import com.mohaberabi.jethabbit.core.data.database.entity.toPendingEntity
import com.mohaberabi.jethabbit.core.data.database.entity.toPendingModel
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitSyncLocalDataSource
import javax.inject.Inject

class RoomPendingHabitLocalDataSource @Inject constructor(
    private val pendingHabitDao: HabitSyncDao
) : HabitSyncLocalDataSource {
    override suspend fun insertHabitSync(pending: PendingHabitModel): EmptyDataResult<DataError> {
        return try {
            pendingHabitDao.insertSyncHabit(pending.toPendingEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteHabitSync(habitId: String): EmptyDataResult<DataError> {
        return try {
            pendingHabitDao.deleteHabitSync(habitId)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


    override suspend fun getAllPendingHabits(): AppResult<List<PendingHabitModel>, DataError> {

        return try {
            val deletedHabits = pendingHabitDao.getAllSyncHabits().map { it.toPendingModel() }
            AppResult.Done(deletedHabits)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


    override suspend fun deleteAllPendingHabits(): EmptyDataResult<DataError> {
        return try {
            pendingHabitDao.deleteAllPendingHabits()
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun getPendingHabit(id: String): PendingHabitModel? =
        pendingHabitDao.getPendingHabit(id)?.toPendingModel()
}