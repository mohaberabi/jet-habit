package com.mohaberabi.jethabbit.features.habit.domain.repository

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.DataError
import kotlinx.coroutines.flow.Flow

interface HabitRepository {


    suspend fun deleteHabit(habit: Habit): EmptyDataResult<DataError>
    fun getAllHabitsByDayOfWeek(dayOfWeek: String): Flow<List<Habit>>
    suspend fun fetchAndSaveHabits(): EmptyDataResult<DataError>
    suspend fun getHabitById(id: String): Habit?
    suspend fun addHabit(habit: Habit): EmptyDataResult<DataError>
}