package com.mohaberabi.jethabbit.features.habit.domain.source.local

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.DataError
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface HabitLocalDataSource {


    suspend fun deleteHabit(id: String)
    suspend fun addAllHabits(habits: List<Habit>): EmptyDataResult<DataError>
    fun getAllHabitsByDayOfWeek(dayOFWeek: String): Flow<List<Habit>>
    suspend fun getHabitById(id: String): Habit?

    suspend fun deleteAllHabits()
    suspend fun getHabitsSize(): Int
    suspend fun addHabit(habit: Habit): EmptyDataResult<DataError>
}