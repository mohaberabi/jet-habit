package com.mohaberabi.jethabbit.features.habit.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jethabbit.core.data.database.dao.DeletedHabitDao
import com.mohaberabi.jethabbit.core.data.database.entity.toDeletedEntity
import com.mohaberabi.jethabbit.core.data.database.entity.toPendingModel
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.habit.domain.source.local.DeletedHabitLocalDataSource
import javax.inject.Inject

class RoomDeletedHabitLocalDataSource @Inject constructor(
    private val deletedHabitDao: DeletedHabitDao
) : DeletedHabitLocalDataSource {
    override suspend fun insertHabitSync(pending: PendingHabitModel): EmptyDataResult<DataError> {
        return try {
            deletedHabitDao.insertDeletedHabit(pending.toDeletedEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteHabitSync(habitId: String): EmptyDataResult<DataError> {
        return try {
            deletedHabitDao.deleteDeletedHabit(habitId)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


    override suspend fun getAllPendingDeletedHabits(): AppResult<List<PendingHabitModel>, DataError> {

        return try {
            val deletedHabits = deletedHabitDao.getAllDeletedHabits().map { it.toPendingModel() }
            AppResult.Done(deletedHabits)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }


    override suspend fun deleteAllDeletedHabits(): EmptyDataResult<DataError> {
        return try {
            deletedHabitDao.deleteAllDeletedHabits()
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun getDeletedHabit(id: String): PendingHabitModel? =
        deletedHabitDao.getDeletedHabit(id)?.toPendingModel()
}