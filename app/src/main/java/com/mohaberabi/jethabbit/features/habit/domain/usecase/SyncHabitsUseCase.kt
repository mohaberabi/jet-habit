package com.mohaberabi.jethabbit.features.habit.domain.usecase

import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import javax.inject.Inject

class SyncHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {

    suspend operator fun invoke() = habitRepository.fetchAndSaveHabits()
}