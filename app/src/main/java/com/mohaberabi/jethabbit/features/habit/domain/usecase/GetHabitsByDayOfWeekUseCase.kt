package com.mohaberabi.jethabbit.features.habit.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime
import javax.inject.Inject

class GetHabitsByDayOfWeekUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {

    operator fun invoke(dayOfWeek: String): Flow<List<Habit>> =
        habitRepository.getAllHabitsByDayOfWeek(dayOfWeek)
}