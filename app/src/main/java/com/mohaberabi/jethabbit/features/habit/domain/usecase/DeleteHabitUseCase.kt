package com.mohaberabi.jethabbit.features.habit.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import javax.inject.Inject


class DeleteHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {


    suspend operator fun invoke(habit: Habit): EmptyDataResult<DataError> =
        habitRepository.deleteHabit(habit)
}