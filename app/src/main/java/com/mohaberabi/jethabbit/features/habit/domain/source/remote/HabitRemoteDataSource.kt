package com.mohaberabi.jethabbit.features.habit.domain.source.remote

import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.DataError

interface HabitRemoteDataSource {


    suspend fun deleteHabit(id: String, uid: String): EmptyDataResult<DataError>

    suspend fun getAllHabits(uid: String): AppResult<List<Habit>, DataError>
    suspend fun addHabit(habit: Habit, uid: String): EmptyDataResult<DataError>
}