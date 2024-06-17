package com.mohaberabi.jethabbit.features.habit.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jethabbit.core.data.database.dao.HabitDao
import com.mohaberabi.jethabbit.core.data.database.entity.toHabit
import com.mohaberabi.jethabbit.core.data.database.entity.toHabitEntity
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.ZonedDateTime
import javax.inject.Inject

class RoomHabitLocalDataSource @Inject constructor(
    private val habitDao: HabitDao,
) : HabitLocalDataSource {
    override suspend fun addAllHabits(habits: List<Habit>): EmptyDataResult<DataError> {
        return try {
            habitDao.insertAllHabits(habits.map { it.toHabitEntity() })
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun getHabitsSize(): Int = habitDao.getHabitsCount()
    override fun getAllHabitsByDayOfWeek(dayOFWeek: String): Flow<List<Habit>> =
        habitDao.getAllHabits().map { list -> list.map { it.toHabit() } }

    override suspend fun getHabitById(id: String): Habit? =
        habitDao.getHabitById(id)?.toHabit()

    override suspend fun addHabit(habit: Habit): EmptyDataResult<DataError> {
        return try {
            habitDao.insertHabit(habit.toHabitEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllHabits() = habitDao.deleteAllHabits()

    override suspend fun deleteHabit(id: String) = habitDao.deleteHabit(id)
}